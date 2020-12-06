package cn.itlym.platform.search;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lym
 */
public class HotSearchCount {

    /**
     * 本地缓存时长，一般建议使用秒为单位，默认记录最近 2 分钟的
     */
    private static final Node[] buffer = new Node[128];

    /**
     * 每秒不同搜索一般在多少次
     */
    private static final int DISTINCT_EVERY_SECOND = 500;

    /**
     * buffer handle 用于 CAS
     */
    private final VarHandle nodeHandle = MethodHandles.arrayElementVarHandle(Node[].class);

    public void addSearchWord(String searchWord) {
        // fixme 改为秒钟数
        long currentTime = System.currentTimeMillis();
        int index = ((int) (currentTime)) & (buffer.length - 1);
        Node currentNode = getNodeAt(index);
        if(currentNode.timeStamp != currentTime) {
            // 需要 new
            Node newNode = new Node(currentTime, DISTINCT_EVERY_SECOND);
            if(!casNodeAt(index, currentNode, newNode)){
                currentNode = getNodeAt(index);
            }
        }
        AtomicInteger count = currentNode.computeIfAbsent(searchWord, k -> new AtomicInteger(0));
        count.incrementAndGet();
    }


    public List<Node> takeBefore(long timeStamp) {
        // 若频率固定，可以使用 ArrayList，并设置大小
        List<Node> result = new LinkedList<>();
        int index = ((int) (timeStamp)) & (buffer.length - 1);
        for (int i = 0; i < buffer.length; i++) {
            Node node = getNodeAt(i);
            if(node.timeStamp < timeStamp) {
                result.add(node);
            }
        }
        return result;

    }

    private Node getNodeAt(int index) {
        // 获取 buffer 数组的第 index 个元素
        return (Node) nodeHandle.get(buffer, index);
    }

    private boolean casNodeAt(int index, Node old, Node newValue) {
        // 如果 buffer 第 index 个元素是 old，则该元素被设为 newValue
        return nodeHandle.compareAndSet(buffer, index, old, newValue);
    }


    /**
     * 这里使用 Map 结构
     * 为了避免突发大量不同词频带来的扩容以及垃圾回收，可以替换为大顶堆
     * 但大顶堆会因数据分散带来意外换出，导致某些词本应统计为热词而未统计（与分散程度相关，宏观上不会影响过多）
     */
    public static class Node extends ConcurrentHashMap<String, AtomicInteger> {

        /**
         * 什么时候产生的热词（秒钟）
         */
        final long timeStamp;

        /**
         * 创建
         * @param timeStamp 时间戳
         * @param size 一般来源预估每秒搜索不同的次不超过多少次
         */
        public Node(long timeStamp, int size) {
            super(size);
            this.timeStamp = timeStamp;
        }

        /**
         * 默认每秒约 500 次不同搜索
         * @param timeStamp 时间戳
         */
        public Node(long timeStamp) {
            this(timeStamp, 500);
        }

    }


}
