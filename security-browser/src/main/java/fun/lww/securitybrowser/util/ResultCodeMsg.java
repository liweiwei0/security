package fun.lww.securitybrowser.util;

public enum ResultCodeMsg {

    SUCCESS(1001, "成功"),
    ERROR(1002, "异常"),
    FAIL(1003, "失败")
    ;

    private Integer code;

    private String msg;

    ResultCodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
