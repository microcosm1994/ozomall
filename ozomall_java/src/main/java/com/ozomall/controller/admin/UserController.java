package com.ozomall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

@Controller
public class UserController {

    @ResponseBody
    @RequestMapping("/login")
    public String login() {
        return "登陆成功";
    }
}
