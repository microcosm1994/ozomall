import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/buyApi.dart';
import 'package:ozomall_flutter/utils/server.dart';
import 'package:ozomall_flutter/widget/ensureBox/index.dart';
import 'package:ozomall_flutter/widget/staggeredListView/index.dart';
import 'package:ozomall_flutter/widget/swiper/index.dart';

class Buy extends StatefulWidget {
  Buy({Key key}) : super(key: key);

  @override
  _BuyState createState() => _BuyState();
}

class _BuyState extends State<Buy> with SingleTickerProviderStateMixin {
  TabController _tabController; // tabbar控制器
  int currentIndex = 0;
  List bannerList = [];
  List titles = [
    "推荐",
    "数码",
    "鞋类",
    "服装",
    "手表",
    "箱包",
    "配饰",
    "潮玩",
    "运动",
    "美妆",
    "家居",
    "汽车",
    "家电"
  ];
  String searchValue = "";
  // 获取banner
  void getBanner() {
    BuyApi.getBanner().then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          bannerList = res["data"];
          print(bannerList);
        });
      }
    });
  }

  @override
  void initState() {
    // tabbar控制器
    _tabController = new TabController(
        initialIndex: currentIndex,
        vsync: this, //固定写法
        length: titles.length //指定tab长度
        );
    getBanner(); // 获取banner
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xf5f5f5f5),
      appBar: PreferredSize(
          child: buildAppBar(), preferredSize: Size.fromHeight(100)),
      body: buildTabBarView(),
    );
  }

  // AppBar
  AppBar buildAppBar() {
    return AppBar(
      actions: [
        Padding(
            padding: EdgeInsets.only(right: 10.0),
            child: Container(
                width: 60,
                child: FlatButton(
                  child: Text(
                    "ALL",
                    style: TextStyle(color: Color(0xff56C0C1)),
                  ),
                  onPressed: () {
                    print("all");
                  },
                )))
      ],
      automaticallyImplyLeading: true,
      leading: null,
      backgroundColor: Colors.white,
      elevation: 0, // 消除底部阴影
      title: Container(
        height: 40,
        child: buildTextField(),
      ),
      bottom: buildTabbar(),
    );
  }

  // TextField
  TextField buildTextField() {
    final controller = TextEditingController(); // 输入框控制器
    controller.text = searchValue;
    return TextField(
      readOnly: true, // 设置只读
      maxLines: 1, //最大行数
      autocorrect: false, //是否自动更正
      style: TextStyle(fontSize: 14.0, color: Colors.black87), //输入文本的样式
      decoration: InputDecoration(
          fillColor: Colors.grey[200],
          filled: true,
          disabledBorder: InputBorder.none,
          enabledBorder: InputBorder.none,
          focusedBorder: InputBorder.none,
          hintText: '搜索内容',
          // 输入框前面的小部件
          prefixIcon: Icon(
            Icons.search,
            color: Colors.black54,
          ),
          //输入框后面的小部件
          suffixIcon: IconButton(
              icon: Icon(
                Icons.camera_alt,
                color: Colors.black54,
              ),
              onPressed: () {
                print("camera_alt");
              })),
      // 点击跳转到搜索页
      onTap: () {
        Navigator.pushNamed(context, "/search");
      },
      controller: controller,
    );
  }

  // Tabbar
  Widget buildTabbar() {
    List<Widget> tabs = [];
    for (var i = 0; i < titles.length; i++) {
      Tab tab = Tab(
        child: Text(titles[i]),
      );
      tabs.add(tab);
    }
    return TabBar(
        labelColor: Colors.black87, // 选中颜色
        labelStyle: TextStyle(fontSize: 14),
        unselectedLabelColor: Colors.black54, // 未选中颜色
        controller: _tabController,
        tabs: tabs,
        isScrollable: true, // 是否可以滚动
        indicatorWeight: 0.1);
  }

  // 选项卡视图容器
  TabBarView buildTabBarView() {
    List<Widget> tabbarViewList = [];
    for (var i = 0; i < titles.length; i++) {
      Widget view;
      // 购买首页布局
      if (i == 0) {
        view = ListView(
          children: <Widget>[
            SwiperCustom(swiperList: bannerList), // 轮播图
            EnsureBox(), // 正品保障、逐件查验、多重鉴别
            buildGridView(), // 九宫格菜单
            StaggeredListView() // 列表
          ],
        );
      } else {
        // 其他页面
        view = StaggeredListView();
      }
      tabbarViewList.add(view);
    }
    return TabBarView(controller: _tabController, children: tabbarViewList);
  }

  // GridView
  Widget buildGridView() {
    List arr = ["新品速递", "热销榜单", "男款羽绒服", "李宁外套", "人气潮服", "人气热卖", "Y-3", "MORE"];
    var gridView = GridView.builder(
        shrinkWrap: true,
        physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 4, childAspectRatio: 2.0),
        itemCount: 8, // 元素数量
        itemBuilder: (BuildContext context, int index) {
          return Column(children: [
            Container(
                height: 30.0,
                child: Image.asset(
                  "lib/assets/u=1691093550,1795227220&fm=26&gp=0.jpg",
                  fit: BoxFit.cover,
                )),
            Expanded(
                child: Text(
              arr[index],
              style: TextStyle(fontSize: 12.0, color: Colors.black54),
            ))
          ]);
        });
    return Container(
      height: 110,
      child: gridView,
      color: Colors.white,
      margin: EdgeInsets.only(top: 4),
    );
  }
}
