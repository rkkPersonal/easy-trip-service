package com.easy.trip.service.login.impl;

import com.easy.trip.common.constant.Constant;
import com.easy.trip.common.vo.LoginVo;
import com.easy.trip.service.login.AbstractLoginService;
import org.springframework.stereotype.Service;

/**
 * @author Steven
 * @date 2022年09月24日 23:58
 */
@Service(Constant.Login.DEFAULT)
public class DefaultLoginServiceImpl extends AbstractLoginService {


    @Override
    protected void doLogin(LoginVo loginVo) {


    }
}