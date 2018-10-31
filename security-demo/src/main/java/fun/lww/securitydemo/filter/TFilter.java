package fun.lww.securitydemo.filter;

import javax.servlet.*;
import java.io.IOException;

//过滤器 @Component注解将过滤器交给spring管理
//@Component
public class TFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TFilter init 过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TFilter doFilter");

        Long starttime = System.currentTimeMillis();

        //执行到业务方法或下一个filter
        filterChain.doFilter(servletRequest, servletResponse);

        Long endtime = System.currentTimeMillis();
        System.out.println("filter 耗时 " + (endtime - starttime));
    }

    @Override
    public void destroy() {
        System.out.println("TFilter destroy 过滤器销毁");
    }
}
