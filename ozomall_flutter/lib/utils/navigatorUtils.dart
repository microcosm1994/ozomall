import 'package:shared_preferences/shared_preferences.dart';

class NavigatorUtils {
  // 获取登陆前的路由
  static getRoute() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString("beforeLoginRoute");
  }

  // 存储登陆前的路由
  static setRoute(String val) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("beforeLoginRoute", val);
  }

  // 清除登陆前的路由
  static clearRoute() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove("beforeLoginRoute");
  }
}
