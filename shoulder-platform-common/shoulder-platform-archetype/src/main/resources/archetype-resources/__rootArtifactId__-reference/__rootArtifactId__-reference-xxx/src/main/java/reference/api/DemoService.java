#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import org.shoulder.core.dto.response.BaseResult;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
