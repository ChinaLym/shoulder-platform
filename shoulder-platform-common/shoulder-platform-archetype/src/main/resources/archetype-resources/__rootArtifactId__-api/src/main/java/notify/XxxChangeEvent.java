#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.notify;

import lombok.Data;

/**
 * xxx变更通知
 *
 * @author ${author}
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
