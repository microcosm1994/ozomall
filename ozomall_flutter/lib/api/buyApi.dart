import 'package:ozomall_flutter/utils/server.dart';

class BuyApi {
  // 获取banner
  static Future getBanner(params) {
    return Server.get("/banner/get", params);
  }

   // 获取商品列表
  static Future getGoodsList(params) {
    return Server.get("/mall/goods/list", params);
  }
}
