package cn.itlym.platform.uaa.notify;

import lombok.Data;

/**
 * xxx变更通知
 *
 * @author shoulder
 */
@Data
public class XxxChangeEvent {

    private String type;

    private String key;

    private Object value;

    /**
     * 配置项版本号，每次变更加一
     */
    private Integer version;

}
