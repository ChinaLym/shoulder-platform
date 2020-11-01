package cn.itlym.platform.uaa.api;

import org.shoulder.core.dto.response.RestResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * demo restful api
 *
 * @author shoulder
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
    RestResult configItem(@PathParam("key") String key);

}
