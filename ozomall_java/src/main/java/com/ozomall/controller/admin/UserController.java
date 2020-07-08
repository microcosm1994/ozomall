package com.ozomall.controller.admin;

import com.google.code.kaptcha.Constants;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminUserService;
import com.ozomall.utils.Encryption;
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
        Result result = ResultGenerate.genSuccessResult();
        Encryption encryption = new Encryption();
        // 获取验证码
        String kaptchaCode = loginInfo.get("code");
        Object kaptchaSessionCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(kaptchaCode);
        System.out.println(kaptchaSessionCode);
        if (kaptchaCode.equals(kaptchaSessionCode)) {
            // 验证码正确
            Boolean isLogin = adminUserService.login(loginInfo.get("userName"), loginInfo.get("passWord"));
            if (isLogin) {
                result = ResultGenerate.genSuccessResult("登陆成功");
            } else {
                result = ResultGenerate.genErroResult("用户名或密码错误");
            }
            System.out.println(result.toString());
        } else {
            // 验证码错误
            result = ResultGenerate.genErroResult("验证码错误");
        }
        return result;
    }
}
