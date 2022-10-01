package com.easy.trip.controller.pay.notify;

import com.alipay.api.internal.util.AlipaySignature;
import com.easy.trip.common.util.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.easy.trip.service.pay.impl.AlipayService.*;

/**
 * @author Steven
 * @date 2022年09月25日 11:15
 */
@RestController
@RequestMapping("/api/notify")
@Slf4j
public class NotifyController {

    @RequestMapping("/alipay")
    public String alipayNotify(HttpServletRequest request) throws Exception {
        //将异步通知中收到的所有参数都存放到 map 中
        Map<String, String> params = Parameter.parseMap(request.getParameterMap());
        // 日志打印回调信息，包括签名，支付状态，所有参数
        log.info("alipay_callback, sign:{}, trade_status:{}, params:{}", params.get("sign"), params.get("trade_status"), params.toString());

        // 需要除去sign、sign_type两个参数，而sign已经在#rsaCheckV2方法中除去了
        params.remove("sign_type");

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商家自身业务处理，校验失败返回failure
            log.info("验签成功后");
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            log.info("验签失败");
        }
        log.info(params.toString());
        return "success";
    }
}
