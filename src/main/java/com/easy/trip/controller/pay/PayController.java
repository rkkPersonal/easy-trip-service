package com.easy.trip.controller.pay;

import com.easy.trip.common.enums.PayCodeEnum;
import com.easy.trip.common.constant.Constant;
import com.easy.trip.common.vo.OrderVo;
import com.easy.trip.service.pay.AbstractPayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Steven
 * @date 2022年09月25日 11:09
 */
@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Resource
    private Map<String, AbstractPayService> payServiceMap;

    @GetMapping("/execute/{payCode}")
    public void pay(@PathVariable("payCode") Integer payCode,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        AbstractPayService payService = payServiceMap.get(PayCodeEnum.getPayServiceByCode(payCode));
        OrderVo orderVo = OrderVo.builder().transType(Constant.TransType.PAY).build();
        payService.pay(orderVo,request,response);

    }
}
