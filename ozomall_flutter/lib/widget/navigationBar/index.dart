import 'package:flutter/material.dart';
import 'package:ozomall_flutter/model/sys.dart';
import 'package:provider/provider.dart';

class NavigationBar extends StatefulWidget {
  NavigationBar({Key key}) : super(key: key);

  @override
  _NavigationBarState createState() => _NavigationBarState();
}

class _NavigationBarState extends State<NavigationBar> {
  @override
  Widget build(BuildContext context) {
    //获取CounterProvider
    SysProvider sysProvider = Provider.of<SysProvider>(context);
    // TODO: implement build
    return BottomNavigationBar(
      currentIndex: sysProvider.currentIndex, // 当前选中的选项卡
      backgroundColor: Colors.white, // 背景色
      type: BottomNavigationBarType.fixed, // 类型
      selectedItemColor: Colors.black87, // 选中状态下的颜色
      selectedFontSize: 10.0, // 选中状态下的字体大小
      unselectedFontSize: 10.0, // 未选中状态下的字体大小
      unselectedItemColor: Colors.black54, // 未选中状态下的颜色
      showSelectedLabels: true,
      showUnselectedLabels: true,
      selectedIconTheme: IconThemeData(color: Colors.black87),
      iconSize: 20.0,
      items: [
        BottomNavigationBarItem(icon: Icon(Icons.home), title: Text("OZO")),
        BottomNavigationBarItem(
            icon: Icon(Icons.local_mall), title: Text("购买")),
        BottomNavigationBarItem(
            icon: Icon(Icons.verified_user), title: Text("服务")),
        BottomNavigationBarItem(icon: Icon(Icons.person), title: Text("我")),
      ],
      // 点击事件
      onTap: (index) {
        sysProvider.setCurrentIndex(index);
      },
    );
  }
}
