package fun.lww.securitydemo.config;

import fun.lww.securitydemo.filter.TFilter;
import fun.lww.securitydemo.interceptor.TInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TInterceptor tInterceptor;

    /**
     * 使用FilterRegistrationBean将filter托管给spring 可以设置filter过滤的请求地址 使用此方法TFilter的注解可以去掉
     * 将TFilter加入spring过滤链
     */
    @Bean
    public FilterRegistrationBean<TFilter> filterRegistration() {
        FilterRegistrationBean<TFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        //将filter添加进spring的filter过滤链
        TFilter tFilter = new TFilter();
        filterRegistrationBean.setFilter(tFilter);

        //设置过滤的请求地址
        List<String> list = new ArrayList<>();
        list.add("/*");
        filterRegistrationBean.setUrlPatterns(list);

        return filterRegistrationBean;
    }

    /**
     * 将拦截器加入spring配置中
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tInterceptor);
    }

}
