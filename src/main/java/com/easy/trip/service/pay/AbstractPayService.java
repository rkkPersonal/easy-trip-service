package com.easy.trip.service.pay;

import com.easy.trip.common.constant.Constant;
import com.easy.trip.common.vo.OrderVo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Steven
 * @date 2022年09月25日 11:10
 */
@Component
public abstract class AbstractPayService {

    public void pay(OrderVo orderVo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String transType = orderVo.getTransType();

        switch (transType) {
            case Constant.TransType.PAY:
                doPay(request,response);
                break;
            case Constant.TransType.TRANSFER_IN:
                transferIn();
                break;
            case Constant.TransType.TRANSFER_OUT:
                transferOut();
                break;
            default:
                break;
        }

    }


    protected abstract void doPay(HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected abstract void transferIn();

    protected abstract void transferOut();

    public abstract void roundQuery();
}
