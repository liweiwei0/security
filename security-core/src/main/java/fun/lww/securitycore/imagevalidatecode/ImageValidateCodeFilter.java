package fun.lww.securitycore.imagevalidatecode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ImageValidateCodeFilter extends OncePerRequestFilter {

    private static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (StringUtils.equals(request.getRequestURL(), "/authentication/form")
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            validate(request);
        }

        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        String code = (String) request.getAttribute("imagecode");
        if (StringUtils.isBlank(code)) {
            throw new ImageValidateCodeException("验证码是空");
        }
        ImageCode imageCode =
                (ImageCode) sessionStrategy.getAttribute(new ServletRequestAttributes(request), SESSION_KEY_IMAGE_CODE);
        if (imageCode == null || imageCode.getImage() == null || StringUtils.isBlank(imageCode.getCode())) {
            throw new ImageValidateCodeException("验证码是空");
        }
        if (!code.equals(imageCode.getCode())) {
            throw new ImageValidateCodeException("验证码错误");
        }
        if (imageCode.getDateTime().isBefore(LocalDateTime.now())) {
            throw new ImageValidateCodeException("验证码过期");
        }
    }

}
