import 'package:flutter/material.dart';
import 'package:ozomall_flutter/widget/navigationBar/index.dart';

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xe2e2e2e2),
      bottomNavigationBar: new NavigationBar(),
    );
  }
}
