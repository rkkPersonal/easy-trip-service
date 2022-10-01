package com.easy.trip.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Steven
 * @date 2022年09月25日 0:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginVo {

    private String code;
    private String state;

    private String accessToken;

}
