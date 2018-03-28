package com.chentongwei.security.core.validate.image;

import com.chentongwei.security.core.validate.code.AbstractValidateCodeProcessor;
import com.chentongwei.security.core.validate.image.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 图形验证码处理器
 *
 * @author chentongwei@bshf360.com 2018-03-27 13:01
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 发送验证码，并将其写到响应中
     *
     * @param request：请求
     * @param validateCode：验证码
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws IOException {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
