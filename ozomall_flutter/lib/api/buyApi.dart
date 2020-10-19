import 'package:ozomall_flutter/utils/server.dart';

class BuyApi {
  // 获取banner
  static Future getBanner(params) {
    return Server().get("/banner/get", params);
  }
}
