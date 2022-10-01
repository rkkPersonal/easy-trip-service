package com.easy.trip.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Steven
 * @date 2022年09月25日 0:13
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(value = "login.info")
@Component
public class AuthorizeProperty {

    private String clientId;

    private String clientSecret;

    private String redirectUrl;

    private String state;

    private String scope;

    private String accessTokenUrl;

    private String authorizeUrl;

}
