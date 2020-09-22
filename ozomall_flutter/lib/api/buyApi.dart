import 'package:ozomall_flutter/utils/server.dart';

class BuyApi {
  static Future getBanner() {
    return Server.get("/banner/get");
  }
}
