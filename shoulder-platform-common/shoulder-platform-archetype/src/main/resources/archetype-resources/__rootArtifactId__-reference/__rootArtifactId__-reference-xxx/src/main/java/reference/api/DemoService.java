#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import org.shoulder.core.dto.response.BaseResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * demo 服务的接口
 *
 * @author ${author}
 */
public interface DemoService {

    /**
     * test
     *
     * @return BaseResult
     */
    BaseResult configItem(String key);

}
