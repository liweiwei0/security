package fun.lww.securitydemo.config;

import fun.lww.securitydemo.exception.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//controller 增强器
@ControllerAdvice
public class TControllerAdvice {

    private Logger log = LoggerFactory.getLogger(getClass());

    //应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("controller 增强器 initBinder");
    }

    //把值绑定到Model中，使全局@RequestMapping可以获取到该值
    @ModelAttribute
    public void addAttributes(Model model) {
        log.info("controller 增强器 addAttributes");
        model.addAttribute("author", "admin");
    }

    //拦截异常并统一处理
    //处理所有controller抛出的异常
    @ExceptionHandler(TException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> myExec(TException myExec) {
        log.info("controller 增强器 处理全局TException异常");
        Map<String, String> map = new HashMap<>();
        map.put("id", myExec.getId().toString());
        map.put("msg", myExec.getMessage());
        log.info("map: {}", map);
        return map;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> runtimeExec(RuntimeException e) {
        System.out.println("controller 增强器 处理全局RuntimeException异常");
        Map<String, String> map = new HashMap<>();
        map.put("msg", e.getMessage());
        log.info("map: {}", map);
        return map;
    }
}
