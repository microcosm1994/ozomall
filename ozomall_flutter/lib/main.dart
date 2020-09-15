import 'package:flutter/material.dart';
import 'package:ozomall_flutter/pages/home/index.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  MyApp({Key key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp (
      routes: <String, WidgetBuilder> {
        "/": (BuildContext context) => Home(),
      },
    );
  }
}
