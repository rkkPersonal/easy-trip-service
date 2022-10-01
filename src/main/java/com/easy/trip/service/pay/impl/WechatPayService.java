package com.easy.trip.service.pay.impl;

import com.easy.trip.common.constant.Constant;
import com.easy.trip.service.pay.AbstractPayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Steven
 * @date 2022年09月25日 11:11
 */
@Service(Constant.Pay.WECHAT_PAY)
public class WechatPayService extends AbstractPayService {

    @Override
    protected void doPay(HttpServletRequest request, HttpServletResponse response) throws Exception{

    }

    @Override
    protected void transferIn() {

    }

    @Override
    protected void transferOut() {

    }

    @Override
    public void roundQuery() {

    }
}