package fun.lww.securitybrowser.config;

import fun.lww.securitybrowser.auth.TAuthenticationFailureHandler;
import fun.lww.securitybrowser.auth.TAuthenticationSuccessHandler;
import fun.lww.securitycore.config.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * browser security配置类
 */
@Configuration
@EnableWebSecurity
public class BrowserConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private TAuthenticationSuccessHandler tAuthenticationSuccessHandler;

    @Autowired
    private TAuthenticationFailureHandler tAuthenticationFailureHandler;

    /**
     * 注入密码加密类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 在内存中设置三个用户
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("zs").password("123456").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
//    }

    /**
     * 配置登录请求拦截信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//        http.httpBasic()
//                .loginPage("/browser-signIn.html")
//                .loginPage("/authentication/require")
//                .loginProcessingUrl("/authentication/form")
                .successHandler(tAuthenticationSuccessHandler)// 登陆成功执行
                .failureHandler(tAuthenticationFailureHandler)// 登陆失败执行
                .and()
                .authorizeRequests()
                .antMatchers("/browser-signIn.html", "/authentication/require",
                        coreProperties.getBrowser().getLoginpage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
