package com.chentongwei.security.core.enums;

import com.chentongwei.security.core.constant.SecurityConstant;

/**
 * 验证码类型枚举
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:28
 */
public enum ValidateCodeType {

    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名称
     * @return
     */
    public abstract String getParamNameOnValidate();
}
