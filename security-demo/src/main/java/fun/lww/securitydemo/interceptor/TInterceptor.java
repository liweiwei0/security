package fun.lww.securitydemo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class TInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器 preHandle");
//        log.info("拦截器 请求的controller " + ((HandlerMethod) handler).getBean().getClass().getName());
//        log.info("拦截器 请求的方法 " + ((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("starttime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("拦截器 postHandle");
        log.info("拦截器 耗时 " + (System.currentTimeMillis() - (Long) request.getAttribute("starttime")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info("拦截器 afterCompletion");
        log.info("拦截器 拦截到的异常信息 " + ex);
    }
}
