package com.easy.trip.service.task;

import com.easy.trip.common.constant.Constant;
import com.easy.trip.service.pay.AbstractPayService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Steven
 * @date 2022年09月25日 19:12
 */
@Configuration
public class OrderRoundQuery {


    @Resource
    private Map<String, AbstractPayService> payServiceMap;

    @Scheduled(cron = "0/2 * * * * ?")
    public void alipayOrderQuery() {
        AbstractPayService payService = payServiceMap.get(Constant.Pay.ALIPAY);
        payService.roundQuery();


    }
}
