package fun.lww.securitydemo.web;

import fun.lww.securitydemo.async.DeferredResultHolder;
import fun.lww.securitydemo.async.MockQueue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 实现多线程访问resetfull接口 异步执行 提高并发和吞吐量
 * 以下单为例
 */
@RestController
@RequestMapping("/order")
public class AsyncOrderController {

    private Logger log = LoggerFactory.getLogger(getClass());
    //设置超时等待时间3000ms 超时返回timeout字符串
    private DeferredResult<String> deferredResult = new DeferredResult<>(3000L, "timeout");
    private SseEmitter sseEmitter = new SseEmitter();

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    //方式一 Callable
    // ==================================================
    /**
     * <p>
     * 使用Callable接口 异步返回业务结果
     * 返回Callable对象时，实际工作线程会在后台处理，Controller无需等待工作线程处理完成，
     * 但Spring会在工作线程处理完毕后才返回客户端。
     * <p>
     * 它的执行流程是这样的：
     * 1.客户端请求服务
     * 2.SpringMVC调用Controller，Controller返回一个Callback对象
     * 3.SpringMVC调用ruquest.startAsync并且将Callback提交到TaskExecutor中去执行
     * 4.DispatcherServlet以及Filters等从应用服务器线程中结束，但Response仍旧是打开状态，也就是说暂时还不返回给客户端
     * 5.TaskExecutor调用Callback返回一个结果，SpringMVC将请求发送给应用服务器继续处理
     * 6.DispatcherServlet再次被调用并且继续处理Callback返回的对象，最终将其返回给客户端
     *
     * @return
     */
    @GetMapping("/placeAnOrder")
    public Callable<String> placeAnOrder() {
        log.info("主线程 开始下单");
        Callable<String> result = () -> {
            log.info("子线程 开始下单");
            Thread.sleep(5000);
            log.info("子线程 实际 下单完成");
            return "success";
        };
        log.info("主线程 下单完成");
        return result;
    }

    // 方式二 DeferredResult
    // ==================================================
    /**
     * <p>
     * 特性：
     * 超时配置：通过构造函数可以传入超时时间，单位为毫秒；因为需要等待设置结果后才能继续处理并返回客户端，如果一直等待会导致客户端一直无响应，因此必须有相应的超时机制来避免这个问题；实际上就算不设置这个超时时间，应用服务器或者Spring也会有一些默认的超时机制来处理这个问题。
     * 结果设置：它的结果存储在一个名称为result的属性中；可以通过调用setResult的方法来设置属性；由于这个DeferredResult天生就是使用在多线程环境中的，因此对这个result属性的读写是有加锁的
     * <p>
     * 处理流程：
     * 1.客户端请求服务
     * 2.SpringMVC调用Controller，Controller返回一个DeferredResult对象
     * 3.SpringMVC调用ruquest.startAsync
     * 4.DispatcherServlet以及Filters等从应用服务器线程中结束，但Response仍旧是打开状态，也就是说暂时还不返回给客户端
     * 5.某些其它线程将结果设置到DeferredResult中，SpringMVC将请求发送给应用服务器继续处理
     * 6.DispatcherServlet再次被调用并且继续处理DeferredResult中的结果，最终将其返回给客户端
     *
     * @return
     */
    @GetMapping("/placeAnOrder1")
    public String placeAnOrder1() {
        log.info("主线程 开始下单");

        String orderNumber = RandomStringUtils.randomNumeric(8);

        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, deferredResult);

        log.info("主线程 下单完成");
        return (String) deferredResult.getResult();
    }

    //例二

    /**
     * 返回DeferredResult对象
     *
     * @return
     */
    @GetMapping("/testDeferredResult")
    public DeferredResult<String> testDeferredResult() {
        return deferredResult;
    }

    /**
     * 对DeferredResult的结果进行设置
     *
     * @return
     */
    @GetMapping("/setDeferredResult")
    public String setDeferredResult() {
        deferredResult.setResult("Test result!");
        return "success";
    }

    // 方式三 SseEmitter
    // ==================================================
    /**
     * 返回SseEmitter对象
     *
     * @return
     */
    @RequestMapping("/testSseEmitter")
    public SseEmitter testSseEmitter() {
        return sseEmitter;
    }

    /**
     * 向SseEmitter对象发送数据
     *
     * @return
     */
    @RequestMapping("/setSseEmitter")
    public String setSseEmitter() {
        try {
            sseEmitter.send(System.currentTimeMillis());
        } catch (IOException e) {
            log.error("IOException!", e);
            return "error";
        }
        return "success";
    }

    /**
     * 将SseEmitter对象设置成完成
     *
     * @return
     */
    @RequestMapping("/completeSseEmitter")
    public String completeSseEmitter() {
        sseEmitter.complete();
        return "complete";
    }

    // 方式四 StreamingResponseBody
    // ==================================================
    /**
     * 用于直接将结果写出到Response的OutputStream中； 如文件下载等
     * @return
     */
    @GetMapping("/stream")
    public StreamingResponseBody handle() {
        return (outputStream) -> {
            String path = "E:\\projects\\security\\security-demo\\src\\main\\resources\\file\\test.txt";
            File file = new File(path);
            outputStream.write(FileUtils.readFileToString(file, "UTF-8").getBytes());
        };
    }

}
