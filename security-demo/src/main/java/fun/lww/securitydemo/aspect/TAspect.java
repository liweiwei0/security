package fun.lww.securitydemo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//切片
@Aspect
@Component
public class TAspect {

    //环绕切面
    //切点是 execution(* fun.lww.securitydemo.web.*.*(..))
    @Around("execution(* fun.lww.securitydemo.web.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("TAspect around");
        Long starttime = System.currentTimeMillis();

        try {
            //执行业务或向下执行下个请求处理
            Object obj = pjp.proceed();
            System.out.println(obj);

            Long endtime = System.currentTimeMillis();
            System.out.println("TAspect 耗时 " + (endtime - starttime));

            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }
}
