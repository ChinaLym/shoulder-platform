package cn.itlym.shoulder.generator;

import cn.itlym.shoulder.generator.service.SysGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.shoulder.core.dto.response.BaseResult;
import org.shoulder.core.dto.response.ListResult;
import org.shoulder.core.util.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器，根据数据库表，生成 Entity、RestApi、Controller、Service、ServiceImpl、Repository、Mapper、Mapper.xml、前端视图
 *
 * @author lym
 */

@MapperScan(value = "cn.itlym.shoulder.generator.mapper")
@Configuration
@SpringBootApplication
@RestController
@RequestMapping("generator")
public class GeneratorApp {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }

    public GeneratorApp(SysGeneratorService sysGeneratorService) {
        this.sysGeneratorService = sysGeneratorService;
    }

    // ======================  由于本工程业务简单，直接写在启动类方便调试  ==========================

    private final SysGeneratorService sysGeneratorService;

    /**
     * ===========【 列出数据库中所有表 】===========
     * <a href="http://localhost:8080/generator/list?page=1&limit=100">列出数据库中所有表</a>
     */
    @ResponseBody
    @RequestMapping("list")
    public BaseResult<ListResult> list(@RequestParam Map<String, Object> params) {
        return BaseResult.success(sysGeneratorService.queryList(params));
    }

    /**
     * ===========【 生成代码 】===========
     * web 中不需要主动关闭流
     * <a href="http://localhost:8080/generator/code?tables=_all">所有表生成代码</a>
     * <a href="http://localhost:8080/generator/code?tables=system_lock">所有表生成代码</a>
     * @param tables 表名，逗号分隔，_all 全部
     */
    @RequestMapping("code")
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
