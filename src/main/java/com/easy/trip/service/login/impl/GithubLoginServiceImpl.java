package com.easy.trip.service.login.impl;

import com.alibaba.fastjson.JSON;
import com.easy.trip.common.config.AuthorizeProperty;
import com.easy.trip.common.constant.Constant;
import com.easy.trip.common.dto.UserDTO;
import com.easy.trip.common.vo.LoginVo;
import com.easy.trip.service.login.AbstractLoginService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

/**
 * @author Steven
 * @date 2022年09月24日 23:53
 * @document https://docs.github.com/cn/developers/apps/building-oauth-apps/authorizing-oauth-apps
 */
@Service(Constant.Login.GITHUB)
public class GithubLoginServiceImpl extends AbstractLoginService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private AuthorizeProperty authorizeProperty;


    /**
     * client_id	string	必填。 从 GitHub 收到的 OAuth App 的客户端 ID。
     * client_secret	string	必填。 从 GitHub 收到的 OAuth App 的客户端密码。
     * code	string	必填。 收到的作为对步骤 1 的响应的代码。
     * redirect_uri	string	用户获得授权后被发送到的应用程序中的 URL。
     *
     * @param loginVo
     */
    @Override
    protected void doLogin(LoginVo loginVo) {
        String httpUrl = UriComponentsBuilder.
                fromUriString(authorizeProperty.getAccessTokenUrl())
                .queryParam("client_id", authorizeProperty.getClientId())
                .queryParam("client_secret", authorizeProperty.getClientSecret())
                .queryParam("code", loginVo.getCode())
                .queryParam("redirect_uri", authorizeProperty.getRedirectUrl()).toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(httpUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        System.out.println(accessTokenResponse.getBody());
        String accessToken = JSON.parseObject(accessTokenResponse.getBody()).getString("access_token");
        UserDTO userProfile = this.getUserProfile(accessToken);
        System.out.println(userProfile);
    }

    @Retryable(maxAttempts = 4, include = RestClientException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
    @Override
    protected UserDTO getUserProfile(String accessToken) {
        String httpUrl = UriComponentsBuilder.fromUriString("https://api.github.com/user").toUriString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", String.format("Bearer %s", accessToken));
        ResponseEntity<String> userProfileResponse = restTemplate.exchange(httpUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        System.out.println(userProfileResponse.getBody());
        return JSON.parseObject(userProfileResponse.getBody(), UserDTO.class);
    }
}
