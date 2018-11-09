package fun.lww.securitydemo.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义验证类配置
 */
public class TValidator implements ConstraintValidator<TConstraint, Object> {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 初始化验证类
     * @param constraintAnnotation
     */
    @Override
    public void initialize(TConstraint constraintAnnotation) {
        log.info("TValidator init");
    }

    /**
     * 判断添加了MyConstraint注解的字段或方法是否为NULL
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        log.info("注解验证 isValid");
        log.info("object: {}", o);
        log.info("constraintValidatorContext: {}", constraintValidatorContext);
        if (o != null) {
            return true;
        }
        return false;
    }
}
