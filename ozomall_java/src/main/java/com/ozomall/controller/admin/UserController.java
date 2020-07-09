package com.ozomall.controller.admin;

import com.google.code.kaptcha.Constants;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminUserService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class UserController {
    @Resource
    private HttpSession session;
    @Resource
    private AdminUserService adminUserService;

    @PostMapping(value = "/user/login")
    public Result login(@RequestBody Map<String, String> loginInfo) {
        // 获取session中的验证码
        Object kaptchaSessionCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(kaptchaSessionCode);
        // 比对验证码
        if (loginInfo.get("code").equals(kaptchaSessionCode)) {
            // 调用登陆方法
            return adminUserService.login(loginInfo.get("userName"), loginInfo.get("passWord"));
        } else {
            // 验证码错误
            return ResultGenerate.genErroResult("验证码错误");
        }
    }
}
