package fun.lww.securitydemo.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /*new Thread(() -> {
            while (true) {
                log.info("循环 监听");
                if (StringUtils.isNotBlank(mockQueue.getPlaceOrder())) {
                    String orderNumber = mockQueue.getPlaceOrder();
                    log.info("返回订单处理结果 {}", orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("success");
                    mockQueue.setPlaceOrder(null);
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
    }
}
