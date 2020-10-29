import 'package:ozomall_flutter/utils/server.dart';

class AddressApi {
  // 添加新地址
  static Future addAddress(data) {
    return Server().post("/mall/address/add", data);
  }

  // 修改地址
  static Future putAddress(data) {
    return Server().post("/mall/address/put", data);
  }

  // 获取地址列表
  static Future getAddress(params) {
    return Server().get("/mall/address/get", params);
  }
}
