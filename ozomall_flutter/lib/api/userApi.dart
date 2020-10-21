import 'package:ozomall_flutter/utils/server.dart';

class UserApi {
  // 发送短信验证码
  static Future sendMessage(data) {
    return Server().post("/mall/user/sendMessage", data);
  }

   // 登录
  static Future phoneLogin(data) {
    return Server().post("/mall/user/phoneLogin", data);
  }

   // 登出
  static Future logout() {
    return Server().post("/mall/user/logout");
  }

  // 获取用户设置
  static Future getSettings(params) {
    return Server().get("/mall/user/getSettings", params);
  }
}
