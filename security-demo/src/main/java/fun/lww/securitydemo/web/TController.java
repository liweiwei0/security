package fun.lww.securitydemo.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//测试
@RestController
public class TController {

    //测试ControllerAdvice addAttributes方法效果
    //通过ModelMap对象或@ModelAttribute注解 获取在TControllerAdvice.addAttributes方法set的值
    @RequestMapping("/test1")
    public void test1(@ModelAttribute("author") String author) {
        System.out.println(author);
    }

    @RequestMapping("/test2")
    public void test2(ModelMap modelMap) {
        System.out.println(modelMap.get("author"));
    }

}
