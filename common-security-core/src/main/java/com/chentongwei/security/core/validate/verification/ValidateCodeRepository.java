package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码存取器
 *
 * @author TongWei.Chen 2018-04-09 20:02
 **/
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request：请求
     * @param validateCode：验证码
     * @param validateCodeType：验证码类型
     */
    void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param request：请求
     * @param validateCodeType：验证码类型
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param request：请求
     * @param validateCodeType：验证码类型
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);
}
