#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.impl;

import org.shoulder.core.dto.response.BaseResult;
import ${package}.api.DemoService;

/**
 * 实现调用 DemoService
 *
 * @author ${author}
 */
public class DemoServiceClient implements DemoService {

    /**
     * configItem
     *
     * @return BaseResult
     */
    public BaseResult configItem(String key) {
        System.out.print("假装调了一次接口");
    }

}
