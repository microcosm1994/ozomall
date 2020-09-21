import 'package:flutter/material.dart';
import 'package:ozomall_flutter/model/sys.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/indexPage/indexPage.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
import 'package:ozomall_flutter/widget/navigationBar/index.dart';
import 'package:provider/provider.dart';

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // 页面列表
  List viewList = [IndexPage(), Buy(), MyPage(), MyPage()];
  @override
  Widget build(BuildContext context) {
    //获取CounterProvider
    SysProvider sysProvider = Provider.of<SysProvider>(context);
    return Scaffold(
      bottomNavigationBar: new NavigationBar(), // 底部选项卡
      body: viewList[sysProvider.currentIndex], // 页面
    );
  }
}
