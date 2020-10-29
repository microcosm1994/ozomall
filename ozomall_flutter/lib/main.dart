import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:ozomall_flutter/pages/address/index.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/home/index.dart';
import 'package:ozomall_flutter/pages/login/index.dart';
import 'package:ozomall_flutter/pages/login/otherPhone.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
import 'package:ozomall_flutter/pages/order/index.dart';
import 'package:ozomall_flutter/pages/search/index.dart';
import 'package:ozomall_flutter/pages/settings/index.dart';
import 'package:ozomall_flutter/widget/store/index.dart';
import 'package:provider/provider.dart';

import 'model/sys.dart';

void main() => runApp(MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (_) => SysProvider(),
        )
      ],
      child: MyApp(),
    ));
final GlobalKey<NavigatorState> navigatorKey = new GlobalKey<NavigatorState>();
final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

class MyApp extends StatefulWidget {
  MyApp({Key key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    EasyLoading.instance
      ..textStyle = TextStyle(fontSize: 12, color: Colors.white);
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: navigatorKey,
      navigatorObservers: [routeObserver],
      onGenerateRoute: (RouteSettings settings) {
        print("route");
        // return MaterialPageRoute(builder: (context) {
        //   String routeName = settings.name;
        //   // 如果访问的路由页需要登录，但当前未登录，则直接返回登录页路由，
        //   // 引导用户登录；其它情况则正常打开路由。
        // });
      },
      onUnknownRoute: (RouteSettings settings) {
        print("route1");
        return MaterialPageRoute(builder: (context) {
          String routeName = settings.name;
          // 如果访问的路由页需要登录，但当前未登录，则直接返回登录页路由，
          // 引导用户登录；其它情况则正常打开路由。
        });
      },
      title: "ozo",
      home: Home(),
      builder: (BuildContext context, Widget child) {
        // 全局loading
        return FlutterEasyLoading(
          child: GestureDetector(
            // 点击空白处键盘消失
            onTap: () {
              FocusScopeNode currentFocus = FocusScope.of(context);
              if (!currentFocus.hasPrimaryFocus &&
                  currentFocus.focusedChild != null) {
                FocusManager.instance.primaryFocus.unfocus();
              }
            },
            child: child,
          ),
        );
      },
      routes: <String, WidgetBuilder>{
        "/store": (BuildContext context) => Store(),
        "/buy": (BuildContext context) => Buy(), // 购买页
        "/my": (BuildContext context) => MyPage(), // 我的
        "/search": (BuildContext context) => Search(), // 搜索页
        "/setting": (BuildContext context) => Setting(), // 个人设置
        "/address": (BuildContext context) => Address(), // 地址列表
        "/login": (BuildContext context) => Login(), // 登陆页
        "/otherPhone": (BuildContext context) => OtherPhone(), // 其他手机号登录页
        "/orderConfirm": (BuildContext context) => OrderConfirm(), // 确认订单页
      },
    );
  }
}
