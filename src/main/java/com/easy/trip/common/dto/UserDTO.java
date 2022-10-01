package com.easy.trip.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Steven
 * @date 2022年09月25日 0:41
 */
@ToString
@Data
public class UserDTO {

    @JSONField(name = "login")
    private String account;

    @JSONField(name = "name")
    private String username;

    private String location;

    private String email;

    @JSONField(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
