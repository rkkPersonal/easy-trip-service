package com.easy.trip.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.easy.trip.common.constant.Constant;
import com.easy.trip.service.pay.AbstractPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Steven
 * @date 2022年09月25日 11:11
 */
@Service(value = Constant.Pay.ALIPAY)
@Slf4j
public class AlipayService extends AbstractPayService {

    public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    public static final String APP_ID = "2016092500591232";
    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFJLDeP2E5YG5gYjUd9XEV5SXyMYbRooHns30R0LyRVvgiOb/L9OMOReGKIpLRanLW/OcmBZwkhScqBHJuLWb2yFe6FHLjv1zJ7Bn657B15GJbuxLYrU5o6CXcbLpcMaRqivySPe8kf675bjWcEeGehL35iYXj9y+HHFofZXj6frQhIeiYTEG3y/OXTSGOs67Ihue6/xCvziK07y1v2yUMCofnnwFmebHhtRgf6dG9J3DAzHDTn6AQOUJ4fWGhf0zs+3MLlp1r8r0tyGqOe/+gF9Rb7MWG7QORNPKa3q5mAYpMB0j2dPS4CIjeGBQPoeK/eW5erk0/nwGQhrBESYG7AgMBAAECggEAKeII4WD+rJXxItDB9NKo6EJVVYLO7lM4mjKUPW50YyQAmoOgGgDHDE5fTJzjoozh409bR1Ksc7CPSWBsvsv5f9g2AIs+OPaRsdNbuNn5YJyfdEu8+fxKliWxA04Qsb/MANgH69kfyVOBL2XD53yYhM0tFbp1K+kuQkpg7090ZxfQWTvKdyhumSthDKZIXTXombL9Ec33cdVjMJEPz8OVAk5NkQtKYEDTtc4PekEiOci71nn3RTp35+A1PJ1F6aITnmCJ4KKMbiSOWD5lU6QWn0po8BKam8yit1F8dAFTivQ08MoTz+Am2JvMgsveker3fze29rnsPF44kb2fT7AOgQKBgQC6iC7sebYQpV1yLlpxr2m+Wu83XnOIJ4IPxKi70uBobqqpkmfdsVi0Qcc9rnuobreFodi21mrYY0sFc2tM3esJXSNnQIN6uevOzEKOPDrE3KZUIH78YDYUrFWFhalx94kvGkSE4LXEdmqi9p4MRcrCGeKUXB3Fx7U7dFWx5S8QaQKBgQC2unmLh8CJ3MNp9xy+uto1YAmlxZwCIKANsu3tSBnFYgb14nd4M799GGgsytQ6QeIweGK3ttoEvZ5OJVM3i/LeudAx7OUT/AX7PE+NOJuAdVlbvlC+vYXHNUlGq1a4KAp8tZj8YvwAKol4P/vzWhA7C1LTeREJ7GirY1ds3w+8gwKBgAxfWhVvYUcrYM5hCri9tNredz2J80nVrQfUuIh0zVO/+2vx/Re0WrjiIBAUUblzVpLzkEdYY4/kziYXaMeZdKyTU/TWT6JFUMHPxJ5gmllSdWnhGestiojGgYmwuvOHQr8wE9loekYEwL/8cbFIs9E6HFBQ59yliXzfE4wQMpX5AoGBAI9+/ThP0SnyjhtHDfHSNs43rxvZhVw+VrKrUyFFv8c75g9uWzewG66XXife2K8vUfuSbqOrGqciaZEgyqsSYhX6gb7TNEpvXhsgD7DtRWCpxxS+7hX8K3R3KiYvI5jXCPVKfuukqBVRUkd2p9tBXQGhMNgVeQX7+26sQtmiim8VAoGBAKnA3d9rHRkjCsvFiyzQzKFkcpAKX5ipl2T7k/NL8zvrcwljj80ZzpSH5tIFj65ySoLiab2vsoi5vp7nxtqes30ua+/4gMIBpLLb7hpQ7sShoXOD1kO3nLTIzaLhs0uK9MD0T5deDrvY6cO8eU0ccc5T7rP3GO1NKV5/9lqvw0Jc";
    public static final String FORMAT = "json";
    public static final String CHARSET = "UTF-8";
    public static final String SIGN_TYPE = "RSA2";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA28Ig0FOVei1gnqDmJI6LkZi9j1rOfHgbHBKJrPKDD2Dudr8UTMOkKGXKuRoaXhnREkrUsadaDp7i5MFF+iBUgX008oubpQQWWYL1Vdpakxb/ijSdo9Uov86NrkemMTZDnH4eF0FY30vJMbwZYeG2EYCOpSeL6xZllmxKdqDxFmD6ZvD9n3yR9QSWbSmJYqMsOcTsqXWHsXaFrW6T7/bFwYnbcLroHmOCRl0TQ/0gdg4jRGvq0FR4TnXK6sh0uM5eYLzDvHXHAONDDlx2JVAhpE74HOF+opRBN2D9b3qlEpoWO+UlUvtDIbbmjqAffSPTbB05j+BRAKp/PIukhoBppwIDAQAB";

    @Override
    protected void doPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(GATEWAY_URL);
        alipayConfig.setAppId(APP_ID);
        alipayConfig.setPrivateKey(PRIVATE_KEY);
        alipayConfig.setFormat(FORMAT);
        alipayConfig.setCharset(CHARSET);
        alipayConfig.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        alipayConfig.setSignType(SIGN_TYPE);
        //构造client
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl("http://9s5n77.natappfree.cc/api/order/page");
        alipayRequest.setNotifyUrl("http://9s5n77.natappfree.cc/api/notify/alipay"); //在公共参数中设置回跳和通知地址
       /* alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }"); //填充业务参数*/

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-", ""));
        bizContent.put("total_amount", 10);
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizContent(bizContent.toString());

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + CHARSET);
        response.getWriter().write(form); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void transferIn() {

    }

    @Override
    protected void transferOut() {

    }

    @Override
    public void roundQuery() {
       /* AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"trade_no\":\"2014112611001004680 073956707\"" +
                "  }");
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }*/
    }
}
