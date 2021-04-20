package cn.itlym.shoulder.platform.system.api;

import org.shoulder.core.dto.response.BaseResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 数据字典
 *
 * @author lym
 */
@Path("/rest/dictionary/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface SysDictionaryRestfulApi {

    /**
     * 字典项
     *
     * @return BaseResult
     */
    @GET
    @Path("/item/{key}")
    BaseResult configItem(@PathParam("key") String key);

}
