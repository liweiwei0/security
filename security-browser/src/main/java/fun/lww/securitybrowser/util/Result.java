package fun.lww.securitybrowser.util;

/**
 * 响应包装类
 * @param <T>
 */
public class Result<T> {

    /**
     * 响应编码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public Result(ResultCodeMsg resultCodeMsg, T data) {
        this.code = resultCodeMsg.getCode();
        this.msg = resultCodeMsg.getMsg();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
