package fun.lww.securitycore.imagevalidatecode;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/imagevalidatecode")
public class ImageValidateCodeController {

    // {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"}
    private static String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑",  "楷体_GB2312" };
    // 可选字符abcdefghjkmnopqrstuvwxyz123456789ABCDEFGHJKMNPQRSTUVWXYZ
    private static String codes = "abcdefghjkmnopqrstuvwxyz123456789ABCDEFGHJKMNPQRSTUVWXYZ";
    // 背景色
    private Color bgColor = new Color(255, 255, 255);
    // 基数(一个文字所占的空间大小)
    private int base = 30;
    // 图像宽度
    private int width = base * 4;
    // 图像高度
    private int height = base;
    // 文字个数(可调)
    private int len = 4;
    // 设置字体大小
    private int fontSize = 22;

    private static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request);
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY_IMAGE_CODE, imageCode);
            ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    private ImageCode createImageCode(HttpServletRequest request) {
        // 1.创建图片缓冲区对象, 并设置宽高和图像类型
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 2.得到绘制环境
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // 3.开始画图
        // 设置背景色
        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);

        StringBuffer sb = new StringBuffer();// 用来装载验证码上的文本

        for (int i = 0; i < len; i++) {
            // 设置画笔颜色 -- 随机
            // g2.setColor(new Color(255, 0, 0));
            g2.setColor(new Color(getRandom(0, 150), getRandom(0, 150),getRandom(0, 150)));

            // 设置字体
            g2.setFont(new Font(fontNames[getRandom(0, fontNames.length)], Font.BOLD, fontSize));

            // 旋转文字(-45~+45)
            int theta = getRandom(-45, 45);
            g2.rotate(theta * Math.PI / 180, 7 + i * base, height - 8);

            // 写字
            String code = codes.charAt(getRandom(0, codes.length())) + "";
            g2.drawString(code, 7 + i * base, height - 8);
            sb.append(code);
            g2.rotate(-theta * Math.PI / 180, 7 + i * base, height - 8);
        }

        // 画干扰线
        for (int i = 0; i < len + 2; i++) {
            // 设置画笔颜色 -- 随机
            // g2.setColor(new Color(255, 0, 0));
            g2.setColor(new Color(getRandom(0, 150), getRandom(0, 150),
                    getRandom(0, 150)));
            g2.drawLine(getRandom(0, 120), getRandom(0, 30), getRandom(0, 120),
                    getRandom(0, 30));
        }

        return new ImageCode(image, sb.toString(), 60);
    }

    /*
     * 生成随机数的方法
     */
    private static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

}
