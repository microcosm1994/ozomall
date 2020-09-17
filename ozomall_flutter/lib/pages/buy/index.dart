import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:ozomall_flutter/widget/swiper/index.dart';

class Buy extends StatefulWidget {
  Buy({Key key}) : super(key: key);

  @override
  _BuyState createState() => _BuyState();
}

class _BuyState extends State<Buy> with SingleTickerProviderStateMixin {
  TabController _tabController; // tabbar控制器
  int currentIndex = 0;
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
  @override
  void initState() {
    // tabbar控制器
    _tabController = new TabController(
        initialIndex: currentIndex,
        vsync: this, //固定写法
        length: titles.length //指定tab长度
        );
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
      onSubmitted: (text) {
        //内容提交(按回车)的回调
        // this.getSearch(text);
        this.searchValue = text;
        debugPrint('submit $text');
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
        view = Column(
          children: [SwiperCustom(), Expanded(child: buildListView())],
        );
      } else {
        view = buildListView();
      }
      tabbarViewList.add(view);
    }
    return TabBarView(controller: _tabController, children: tabbarViewList);
  }

  // listView
  Widget buildListView() {
    return new StaggeredGridView.countBuilder(
      crossAxisCount: 4,
      itemCount: 8,
      padding: EdgeInsets.symmetric(vertical: 4.0),
      itemBuilder: (BuildContext context, int index) => new Container(
          color: Colors.white,
          child: new Center(
            child: new CircleAvatar(
              backgroundColor: Colors.white,
              child: new Text(index.toString()),
            ),
          )),
      staggeredTileBuilder: (int index) =>
          new StaggeredTile.count(2, index.isEven ? 2 : 1),
      mainAxisSpacing: 4.0,
      crossAxisSpacing: 4.0,
    );
  }
}
