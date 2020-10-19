import 'package:flutter/material.dart';
import 'package:ozomall_flutter/pages/address/index.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/home/index.dart';
import 'package:ozomall_flutter/pages/login/index.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
import 'package:ozomall_flutter/pages/search/index.dart';
import 'package:ozomall_flutter/pages/settings/index.dart';
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

class MyApp extends StatefulWidget {
  MyApp({Key key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: navigatorKey,
      title: "ozo",
      routes: <String, WidgetBuilder>{
        "/": (BuildContext context) => Home(),
        "/buy": (BuildContext context) => Buy(),
        "/my": (BuildContext context) => MyPage(),
        "/search": (BuildContext context) => Search(),
        "/setting": (BuildContext context) => Setting(),
        "/address": (BuildContext context) => Address(),
        "/login": (BuildContext context) => Login(),
      },
    );
  }
}
