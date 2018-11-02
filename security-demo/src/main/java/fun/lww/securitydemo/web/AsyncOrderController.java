package fun.lww.securitydemo.web;

import fun.lww.securitydemo.async.DeferredResultHolder;
import fun.lww.securitydemo.async.MockQueue;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

//实现多线程访问reset接口 异步执行 提高并发和吞吐量
@RestController
@RequestMapping("/order")
public class AsyncOrderController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    //使用Callable接口 异步返回业务结果
    @GetMapping("/placeAnOrder")
    public Callable<String> placeAnOrder() {
        log.info("主线程开始");
        Callable<String> result = () -> {
            log.info("子线程 开始下单");
            Thread.sleep(1000);
            log.info("子线程 下单完成");
            return "success";
        };
        log.info("主线程结束");
        return result;
    }

    @GetMapping("/placeAnOrder1")
    public String placeAnOrder1() {
        log.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);

        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, deferredResult);
        log.info("主线程结束");
        return (String) deferredResult.getResult();
    }



}
