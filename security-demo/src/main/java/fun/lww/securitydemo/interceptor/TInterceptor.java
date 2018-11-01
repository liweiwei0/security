package fun.lww.securitydemo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
@Component
public class TInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器 TInterceptor preHandle");
        System.out.println("拦截器 请求的controller " + ((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println("拦截器 请求的方法 " + ((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("starttime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器 TInterceptor postHandle");
        System.out.println("拦截器 耗时 " + (System.currentTimeMillis() - (Long) request.getAttribute("starttime")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        System.out.println("拦截器 TInterceptor afterCompletion");
        System.out.println("拦截器 拦截到的异常信息 " + ex);
    }
}
