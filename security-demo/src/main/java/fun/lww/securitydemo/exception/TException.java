package fun.lww.securitydemo.exception;

//自定义异常类
public class TException extends RuntimeException {

    private Long id;

    public TException(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
