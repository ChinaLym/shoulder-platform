package cn.itlym.shoulder.generator.controller;

import cn.itlym.shoulder.generator.service.SysGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.shoulder.core.dto.response.ListResult;
import org.shoulder.core.dto.response.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author lym
 */
@RestController
@Api(tags = "代码生成器")
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public RestResult<ListResult> list(@RequestParam Map<String, Object> params) {

        return RestResult.success(sysGeneratorService.queryList(params));
    }

    /**
     * 生成代码
     * web 中不需要主动关闭流
     * <a href="http://localhost:8080/generator/code?tables=_all">所有表</a>
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(tables)) {
            throw new IllegalArgumentException("tableName can't be empty");
        }
        response.reset();
        byte[] data = "_all".equals(tables) ? sysGeneratorService.generatorCode(response.getOutputStream())
                :sysGeneratorService.generatorCode(tables.split(","), response.getOutputStream());
        if (data == null || data.length == 0) {
            return;
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
        response.setContentType("application/octet-stream; charset=UTF-8");
        // ClientAbortException: java.io.IOException: 你的主机中的软件中止了一个已建立的连接。加上这行有下载进度，不加可能报错
        response.addHeader("Content-Length", String.valueOf(data.length));

        // response out put stream 会自动关闭
        IOUtils.write(data, response.getOutputStream());

    }


}
