import 'package:flutter/material.dart';

class NavigationBar extends StatefulWidget {
  NavigationBar({Key key}) : super(key: key);

  @override
  _NavigationBarState createState() => _NavigationBarState();
}

class _NavigationBarState extends State<NavigationBar> {
  int currentIndex = 0; // 当前选中的选项卡
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return BottomNavigationBar(
      currentIndex: currentIndex, // 当前选中的选项卡
      backgroundColor: Colors.white, // 背景色
      type: BottomNavigationBarType.fixed, // 类型
      selectedItemColor: Colors.orange, // 选中状态下的颜色
      selectedFontSize: 12.0, // 选中状态下的字体大小
      unselectedFontSize: 12.0, // 未选中状态下的字体大小
      unselectedItemColor: Colors.black54, // 未选中状态下的颜色
      showSelectedLabels: true,
      showUnselectedLabels: true,
      selectedIconTheme: IconThemeData(color: Colors.orange),
      items: [
        BottomNavigationBarItem(icon: Icon(Icons.home), title: Text("OZO")),
        BottomNavigationBarItem(
            icon: Icon(Icons.local_mall), title: Text("购买")),
        BottomNavigationBarItem(
            icon: Icon(Icons.verified_user), title: Text("服务")),
        BottomNavigationBarItem(icon: Icon(Icons.person), title: Text("我")),
      ],
      onTap: _onItemTchange, // 点击事件
    );
  }

  // 点击选项卡触发
  void _onItemTchange(index) {
    setState(() {
      // Navigator.pushNamed(context, '/detail');
      currentIndex = index;
    });
  }
}
