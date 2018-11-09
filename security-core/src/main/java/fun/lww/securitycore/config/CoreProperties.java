package fun.lww.securitycore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * core 项目配置类 所有配置中以 sp 开头
 * 核心配置类中加入其余子项目的配置信息
 */
@ConfigurationProperties(prefix = "sp")
public class CoreProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
