import 'package:flutter/material.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/home/index.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
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

class MyApp extends StatefulWidget {
  MyApp({Key key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "ozo",
      routes: <String, WidgetBuilder>{
        "/": (BuildContext context) => Home(),
        "/buy": (BuildContext context) => Buy(),
        "/my": (BuildContext context) => MyPage(),
      },
    );
  }
}
