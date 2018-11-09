package fun.lww.securitycore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置
 */
@Configuration
@EnableConfigurationProperties(CoreProperties.class)
public class CoreSecurityConfig {

}
