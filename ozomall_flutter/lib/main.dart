import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:ozomall_flutter/pages/address/index.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/home/index.dart';
import 'package:ozomall_flutter/pages/login/index.dart';
import 'package:ozomall_flutter/pages/login/otherPhone.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
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
        /// make sure that loading can be displayed in front of all other widgets
        return FlutterEasyLoading(child: child);
      },
      routes: <String, WidgetBuilder>{
        "/store": (BuildContext context) => Store(),
        "/buy": (BuildContext context) => Buy(),
        "/my": (BuildContext context) => MyPage(),
        "/search": (BuildContext context) => Search(),
        "/setting": (BuildContext context) => Setting(),
        "/address": (BuildContext context) => Address(),
        "/login": (BuildContext context) => Login(),
        "/otherPhone": (BuildContext context) => OtherPhone(),
      },
    );
  }
}
