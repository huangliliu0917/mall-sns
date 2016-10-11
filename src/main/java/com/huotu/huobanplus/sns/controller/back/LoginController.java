package com.huotu.huobanplus.sns.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登录逻辑
 * Created by Administrator on 2016/10/10.
 */
@Controller
@RequestMapping("/back")
public class LoginController {
    /**
     * 登录出现问题跳转的逻辑
     * @param model
     * @return
     */
    @RequestMapping("/loginError")
    public String loginError(Model model){
        model.addAttribute("error","error");

        return "/back/login";
    }
}
