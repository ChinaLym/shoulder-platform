package cn.itlym.platform.search;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 从 HotSearchCount 中取走
 * @author lym
 */
public class HotSearchPorter {

    private ConcurrentLinkedQueue<HotSearchCount.Node> queue = new ConcurrentLinkedQueue<>();

    private final AtomicLong lastProcessTimStamp = new AtomicLong(0);

    private HotSearchCount hotSearchCount;


    /**
     * 定期调度该方法，从本地缓存中取
     */
    public void process() {
        // fixme 改为秒钟数
        long currentTime = System.currentTimeMillis();
        long processEnd = currentTime - 5;
        long processStart = lastProcessTimStamp.getAndSet(processEnd);
        // 处理 [processStart, processEnd) 的数据
        List<HotSearchCount.Node> allBeforeEnd = hotSearchCount.takeBefore(processEnd);
        List<HotSearchCount.Node> needProcess = allBeforeEnd.stream()
                .filter(node -> node.timeStamp >= processStart)
                .collect(Collectors.toList());
        // 加到队列里
        // 开一个新线程处理
        doProcess(needProcess);
    }

    /**
     * 可以本地合并xx秒钟的，然后再刷一次记录，减少存储空间，减少读写重复频次
     * 如 QQ 音乐每 10分钟更新一次、微博，每分钟更新一次，网易云音乐每周4更新
     * @param needProcess
     */
    protected void doProcess(List<HotSearchCount.Node> needProcess) {
        // 合并这10分钟的词频，合并结果可采用大顶堆，减少内存占用
        HotSearchCount.Node latest_10_minute = new HotSearchCount.Node(needProcess.get(0).timeStamp, 10240);
        needProcess.forEach(latest_10_minute::putAll);

        // 保存到时序数据库
        persistent(latest_10_minute);

        // 查出 24 小时前 10 分钟的数据

        // 与当前热搜合并（可选）

        // 放入 redis 的 zset


    }

    private void persistent(HotSearchCount.Node node) {

    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE - (-5));
    }

}
