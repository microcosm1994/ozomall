import 'package:ozomall_flutter/utils/server.dart';

class OrdersApi {
  // 获取最近购买订单
  static Future getBuyList(params) {
    return Server.get("/mall/orders/getBuyList", params);
  }
}
