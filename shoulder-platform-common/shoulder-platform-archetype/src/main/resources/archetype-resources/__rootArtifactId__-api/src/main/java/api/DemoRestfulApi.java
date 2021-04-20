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
 * demo restful api
 *
 * @author ${author}
 */
@Path("/rest/demo/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface DemoRestfulApi {

    /**
     * test
     *
     * @return BaseResult
     */
    @GET
    @Path("/hi/{key}")
    BaseResult configItem(@PathParam("key") String key);

}
