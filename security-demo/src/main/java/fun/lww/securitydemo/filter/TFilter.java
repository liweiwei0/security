package fun.lww.securitydemo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤器 @Component注解将过滤器交给spring管理
 */
//@Component
public class TFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化 init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器 doFilter");

        Long starttime = System.currentTimeMillis();

        //执行到业务方法或下一个filter
        filterChain.doFilter(servletRequest, servletResponse);

        Long endtime = System.currentTimeMillis();
        log.info("过滤器 耗时 " + (endtime - starttime));
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁 destroy");
    }
}
