package fun.lww.securitycore.config;

import fun.lww.securitycore.RespType;

/**
 * browser 项目配置类
 */
public class BrowserProperties {

    private String loginpage = "/signIn.html";

    private String resptype = RespType.JSON.getValue();

    public String getLoginpage() {
        return loginpage;
    }

    public void setLoginpage(String loginpage) {
        this.loginpage = loginpage;
    }

    public String getResptype() {
        return resptype;
    }

    public void setResptype(String resptype) {
        this.resptype = resptype;
    }
}
