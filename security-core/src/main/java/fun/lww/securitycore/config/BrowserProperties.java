package fun.lww.securitycore.config;

/**
 * browser 项目配置类
 */
public class BrowserProperties {

    private String loginPage = "/signIn.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
