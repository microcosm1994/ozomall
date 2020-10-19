import 'package:shared_preferences/shared_preferences.dart';

class User {
  // 获取token
  static getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString("token");
  }
}
