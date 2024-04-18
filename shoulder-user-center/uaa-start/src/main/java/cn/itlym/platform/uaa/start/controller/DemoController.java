package cn.itlym.platform.uaa.start.controller;

import org.shoulder.core.log.AppLoggers;
import org.shoulder.core.log.Logger;
import org.shoulder.web.annotation.SkipResponseWrap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一个示例
 *
 * @author shoulder
 */
@SkipResponseWrap // 该类所有方法的返回值将不被包装
@RestController
@RequestMapping("demo")
public class DemoController {

    private static final Logger log = AppLoggers.APP_SERVICE;


    /**
     * 访问 http://localhost:8080/demo/test 测试
     */
    @GetMapping("test")
    public String test() {
        return "Congratulations on your new project based on <a href='https://github.com/ChinaLym/shoulder-platform'>shoulder-platform</a>!</br></br>" +
            "恭喜你成功创建了一个基于<a href='https://github.com/ChinaLym/shoulder-platform'>shoulder-platform</a>的工程！" +
            "shoulder-platform 是借助 <a href='https://github.com/ChinaLym/shoulder-framework'>shoulder-framework</a> 快速开发能力构建的示例平台," +
            "可以 <a href='https://github.com/ChinaLym/shoulder-framework-demo'>点击这里查看使用案例</a>，以快速了解 Shoulder。";
    }


}
