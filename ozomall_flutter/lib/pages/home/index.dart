import 'package:flutter/material.dart';
import 'package:ozomall_flutter/model/sys.dart';
import 'package:ozomall_flutter/pages/buy/index.dart';
import 'package:ozomall_flutter/pages/indexPage/indexPage.dart';
import 'package:ozomall_flutter/pages/my/index.dart';
import 'package:ozomall_flutter/utils/navigatorUtils.dart';
import 'package:ozomall_flutter/utils/userUtils.dart';
import 'package:provider/provider.dart';

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // 页面列表
  List<Widget> viewList = [IndexPage(), Buy(), MyPage(), MyPage()];
  @override
  final pageController = PageController();
  Widget build(BuildContext context) {
    //获取CounterProvider
    SysProvider sysProvider = Provider.of<SysProvider>(context);
    return Scaffold(
      bottomNavigationBar: buildNavigationBar(context), // 底部选项卡
      // body: IndexedStack(
      //     index: sysProvider.currentIndex, //当前的下标
      //     children: viewList //子页面的Widget
      //     ), // 页面
      body: PageView(
        controller: pageController,
        onPageChanged: (int index) {},
        children: viewList,
      ), // 页面
    );
  }

  // bottomNavigationBar
  Widget buildNavigationBar(BuildContext context) {
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
        UserUtils.getToken().then((res) {
          if (index == 3 && res == null) {
            NavigatorUtils.setRoute("/");
            Navigator.pushNamed(context, '/login');
            // pageController.jumpToPage(index);
            // sysProvider.setCurrentIndex(index);
          } else {
            pageController.jumpToPage(index);
            sysProvider.setCurrentIndex(index);
          }
        });
      },
    );
  }
}
