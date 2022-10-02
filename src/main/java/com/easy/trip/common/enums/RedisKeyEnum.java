package com.easy.trip.common.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {

    LOGIN_TOPIC("login","login in ");


    private String key;

    private String message;

    RedisKeyEnum(String key,String message){
        this.key=key;
        this.message=message;
    }
}
