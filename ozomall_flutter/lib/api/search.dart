import 'package:ozomall_flutter/utils/server.dart';

class SearchApi {
  // 获取banner
  static Future search(params) {
    return Server().get("/mall/goods/search", params);
  }
}