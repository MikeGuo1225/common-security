package com.chentongwei.security.validate.enums;

/**
 * 验证码类型
 *
 * @author chentongwei@bshf360.com 2018-05-28 15:00
 */
public enum ValidateCodeTypeEnum {

    /**
     * 图形验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return ValidateCodeParamNameEnum.DEFAULT_PARAMETER_NAME_CODE_IMAGE.value();
        }
    },

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return ValidateCodeParamNameEnum.DEFAULT_PARAMETER_NAME_MOBILE.value();
        }
    },

    /**
     * 极验证
     */
    GEETEST {
        @Override
        public String getParamNameOnValidate() {
            return ValidateCodeParamNameEnum.DEFAULT_PARAMETER_NAME_CODE_GEETEST.value();
        }
    };

    /**
     * 校验验证码的时候，从请求中获取的参数的名称
     *
     * @return
     */
    public abstract String getParamNameOnValidate();

}
