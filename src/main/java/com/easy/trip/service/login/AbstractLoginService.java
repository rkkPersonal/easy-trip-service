package com.easy.trip.service.login;

import com.easy.trip.common.dto.UserDTO;
import com.easy.trip.common.vo.LoginVo;
import org.springframework.stereotype.Component;

/**
 * @author Steven
 * @date 2022年09月24日 23:51
 */
@Component
public abstract class AbstractLoginService {


    public void login(LoginVo loginVo) {

        doLogin(loginVo);
    }

    protected abstract void doLogin(LoginVo loginVo);


    protected  UserDTO getUserProfile(String accessToken){return null;};
}
