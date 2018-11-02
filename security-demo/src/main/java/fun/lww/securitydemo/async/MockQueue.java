package fun.lww.securitydemo.async;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

//模拟队列业务类
@Component
public class MockQueue {

    private String placeOrder;
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        if (StringUtils.isNotBlank(placeOrder)) {
            System.out.println("接到下单请求");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.placeOrder = placeOrder;
            System.out.println("下单完成");
        }
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
