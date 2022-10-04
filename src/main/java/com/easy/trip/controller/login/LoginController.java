package com.easy.trip.controller.login;

import com.easy.trip.common.config.AuthorizeProperty;
import com.easy.trip.common.constant.Constant;
import com.easy.trip.common.exception.LoginException;
import com.easy.trip.common.util.Parameter;
import com.easy.trip.common.vo.LoginVo;
import com.easy.trip.service.login.AbstractLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Steven
 * @date 2022年09月24日 23:43
 */
@Controller
@RequestMapping("/api")
public class LoginController {

    @Resource
    private Map<String, AbstractLoginService> loginServiceMap;
    @Resource
    private AuthorizeProperty authorizeProperty;

    @GetMapping("/authorize")

    public String authorize() {
        String authorizeUrl = String.format(authorizeProperty.getAuthorizeUrl(), authorizeProperty.getClientId(), authorizeProperty.getRedirectUrl(), authorizeProperty.getState());
        return "redirect:"+authorizeUrl;
    }


    /**
     * http://localhost:8089/api/access/callback?code=67fc8f34bf11e82c60b6&state=STATE
     */
    @GetMapping("/access/callback")

    public String getAuthorizeCode(@RequestParam("code") String code, @RequestParam("state") String state) {
        try {
            AbstractLoginService loginService = loginServiceMap.get(Constant.Login.GITHUB);
            loginService.login(LoginVo.builder().code(code).state(state).build());
        } catch (Exception e) {
            throw new LoginException("login faild");
        }
        return "redirect:"+"";
    }

    @RequestMapping("/order/page")
    public ModelAndView redirectToOrderPage(HttpServletRequest request) {
        Map<String, String> orderDetail = Parameter.parseMap(request.getParameterMap());
        orderDetail.remove("sign");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order");
        modelAndView.addObject("orderDetail", orderDetail);
        return modelAndView;
    }

}
