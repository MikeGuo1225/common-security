package com.chentongwei.security.validate.verification;

import com.chentongwei.security.validate.code.ValidateCode;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码的存取删接口，目前实现有session和redis两种方式
 *
 * @author chentongwei@bshf360.com 2018-05-28 15:24
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request：请求
     * @param validateCode：验证码
     * @param validateCodeType：验证码类型
     */
    void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeTypeEnum validateCodeType);

    /**
     * 获取验证码
     *
     * @param request：请求
     * @param validateCodeType：验证码类型
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType);

    /**
     * 移除验证码
     *
     * @param request：请求
     * @param validateCodeType：验证码类型
     */
    void remove(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType);

}
