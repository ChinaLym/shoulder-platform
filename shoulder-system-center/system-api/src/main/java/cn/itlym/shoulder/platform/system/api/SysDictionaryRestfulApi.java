package cn.itlym.shoulder.platform.system.api;

import org.shoulder.core.dto.response.BaseResult;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
