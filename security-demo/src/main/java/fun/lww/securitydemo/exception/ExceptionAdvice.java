package fun.lww.securitydemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

//自定义异常类 处理所有controller抛出的异常
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(TException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> myExec(TException myExec) {
        Map<String, String> map = new HashMap<>();
        map.put("id", myExec.getId().toString());
        map.put("msg", myExec.getMessage());
        System.out.println(map);
        return map;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> runtimeExec(RuntimeException e) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", e.getMessage());
        System.out.println(map);
        return map;
    }
}
