import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

// 获取token
getToken() async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  return prefs.getString("token");
}

// 创建实例
BaseOptions baseOptions = BaseOptions(
    baseUrl: "http://81.68.211.165:8090",
    connectTimeout: 5000,
    receiveTimeout: 3000,
    headers: {"token": "getToken()"});

class Server {
  static Dio _dio = Dio(baseOptions);

  static get(path, params) async {
    Response response = await _dio.get(path, queryParameters: params);
    return response.data;
  }
}
