package com.easy.trip.common.enums;

import com.easy.trip.common.constant.Constant;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PayCodeEnum {

    ALIPAY(0, Constant.Pay.ALIPAY),
    WECHAT_APY(1,Constant.Pay.WECHAT_PAY);

    private Integer payCode;

    private String javaServiceName;

    PayCodeEnum(Integer payCode,String javaServiceName){
        this.payCode=payCode;
        this.javaServiceName=javaServiceName;
    }

    public static String getPayServiceByCode(Integer payCode){
        return Arrays.stream(PayCodeEnum.values()).filter(s->s.getPayCode().equals(payCode)).findFirst().get().getJavaServiceName();
    }
}
