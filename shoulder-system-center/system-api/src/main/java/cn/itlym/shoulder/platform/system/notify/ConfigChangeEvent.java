package cn.itlym.shoulder.platform.system.notify;

import lombok.Data;

/**
 * 配置变更通知
 *
 * @author lym
 */
@Data
public class ConfigChangeEvent {

    private String type;

    private String key;

    private Object value;

    /**
     * 配置项版本号，每次变更加一
     */
    private Integer version;

}
