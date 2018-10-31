package fun.lww.securitydemo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//自定义验证类配置
public class TValidator implements ConstraintValidator<TConstraint, Object> {
    @Override
    public void initialize(TConstraint constraintAnnotation) {
        System.out.println("TValidator init");
    }

    //判断添加了MyConstraint注解的字段或方法是否为NULL
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o);
        System.out.println(constraintValidatorContext);
        if (null != o) {
            return true;
        }
        return false;
    }
}
