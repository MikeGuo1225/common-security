package com.chentongwei.security.validate.code.image;

import com.chentongwei.security.validate.code.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 图形验证码处理器
 *
 * imageValidateCodeProcessor ==> {@link com.chentongwei.security.validate.code.ValidateCodeProcessorHolder}
 *
 * @author chentongwei@bshf360.com 2018-05-28 14:52
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 将验证码发到客户端。
     *
     * @param request：请求
     * @param code：验证码
     * @throws IOException
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode code) throws IOException {
        request.getResponse().getWriter().print(code.getImage());
    }
}
