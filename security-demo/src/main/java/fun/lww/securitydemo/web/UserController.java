package fun.lww.securitydemo.web;

import com.fasterxml.jackson.annotation.JsonView;
import fun.lww.securitydemo.bean.User;
import fun.lww.securitydemo.exception.TException;
import fun.lww.securitydemo.interfac.UserDetail;
import fun.lww.securitydemo.interfac.UserSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1L, "zs", "123456"));
        users.add(new User(2L, "ls", "123456"));
        users.add(new User(3L, "ww", "123456"));
    }

    /**
     * @JsonView 注解可以在调用接口 返回同一对象不同属性 注意 需要定义注解中添加的接口 及在实体属性上使用注解
     */
    @GetMapping
    @JsonView(UserSimple.class)
    public List<User> users() {
        log.info("users {}", users);
        return users;
    }

    @GetMapping("/query")
    @JsonView(UserSimple.class)
    public List<User> query(@RequestParam(required = false) String name, @PageableDefault(size = 2, page = 1, sort = "id") Pageable pageable) {
        log.info("name {}", name);
        log.info("pageable {}", pageable);
        return users;
    }

    /**
     * 使用正则表达式验证参数
     *
     * GetMapping("/{id:1}")
     * id:1 只会匹配id=1的请求
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(UserDetail.class)
    public User userinfo(@PathVariable(name = "id") Long id) {
        log.info("id {}", id);
//        throw new IOException("出异常了");
        return users.get(0);
    }

    /**
     * 使用@Valid注解进行属性非空验证 错误信息在BindingResult中可以获取
     * Valid注解可以单独使用 BindingResult需和@Valid注解一同使用
     */
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        log.info("user {}", user);
        log.info("errors {}", errors);
        if (errors.hasErrors()) {
            throw new TException(-1L);
        }
        user.setId(10L);
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public Long update(@PathVariable("id") Long id, @Valid @RequestBody User user, BindingResult errors) {
        log.info("id {}", id);
        log.info("user {}", user);
        log.info("errors {}", errors);
        throw new RuntimeException("抛出异常");
//        return id;
    }

    @DeleteMapping("/{id:\\d+}")
    public Long delete(@PathVariable("id") Long id) {
        log.info("id {}", id);
        return id;
    }
}
