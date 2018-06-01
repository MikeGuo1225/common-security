package com.chentongwei.security.validate.code.geetest;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.validate.code.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 极验证处理器
 *
 * @author TongWei.Chen 2018-03-30 22:47:15
 */
@Component("geetestValidateCodeProcessor")
public class GeetestCodeProcessor extends AbstractValidateCodeProcessor<GeetestCode> {

    @Override
    protected void send(ServletWebRequest request, GeetestCode validateCode) throws Exception {
        request.getResponse().getWriter().print(JSON.toJSONString(validateCode));
    }
}
