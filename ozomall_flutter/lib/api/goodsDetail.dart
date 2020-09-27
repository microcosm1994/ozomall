import 'package:ozomall_flutter/utils/server.dart';

class GoodsDetailApi {
  // 获取商品详情
  static Future getGoodsDetail(params) {
    return Server.get("/goods/get", params);
  }

   // 获取商品图片
  static Future getGoodsPic(params) {
    return Server.get("/goods/getGoodsPic", params);
  }
}
