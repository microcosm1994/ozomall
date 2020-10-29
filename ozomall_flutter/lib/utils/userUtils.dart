import 'dart:convert';

import 'package:ozomall_flutter/api/userApi.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserUtils {
  // 获取token
  static getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString("token");
  }

  // 存储token
  static setToken(String val) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("token", val);
  }

  // 清除token
  static clearToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove("token");
  }

  // 获取用户信息
  static getUserInfo() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String userInfo = prefs.getString("users");
    if (userInfo == null) return null;
    return jsonDecode(userInfo);
  }

  // 存储用户信息
  static setUserInfo(Map data) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("users", jsonEncode(data));
  }

  // 清除用户信息
  static clearUserInfo() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove("users");
  }

  // 从服务器获取用户设置信息(请求接口)
  static getSettings() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var users = jsonDecode(prefs.getString("users"));
    UserApi.getSettings({"userId": users["id"]}).then((res) {
      if (res["code"] == 1) {
        prefs.setString("userSetting", jsonEncode(res["data"]));
      }
    });
  }

  // 获取本地保存的用户设置信息
  static getUserSettings() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String userSetting = prefs.getString("userSetting");
    if (userSetting == null) return null;
    return jsonDecode(userSetting);
  }

  // 设置用户设置信息
  static setSettings(Map data) async {
    UserApi.setSettings(data).then((res) {
      if (res["code"] == 1) {
        getSettings();
      }
    });
  }

  // 清除用户设置信息
  static clearSettings() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove("userSetting");
  }
}
