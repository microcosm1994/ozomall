import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class Recommend extends StatefulWidget {
  Recommend({Key key}) : super(key: key);

  @override
  _RecommendState createState() => _RecommendState();
}

class _RecommendState extends State<Recommend>
    with SingleTickerProviderStateMixin {
  TabController _tabController;
  int currentIndex = 0;
  List titles = [
    "全部",
    "球鞋",
    "穿搭",
    "健身",
    "美妆",
    "男士理容",
    "手表",
    "数码",
    "玩具",
    "运动",
    "汽车",
    "游戏"
  ];

  @override
  void initState() {
    _tabController = new TabController(
        initialIndex: currentIndex,
        vsync: this, //固定写法
        length: titles.length //指定tab长度
        );
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> tabbarViewList = [];
    for (var i = 0; i < titles.length; i++) {
      Widget view = buildListView();
      tabbarViewList.add(view);
    }
    return Column(children: [
      Container(
        color: Colors.white,
        child: buildTabbar()
      ),
      Expanded(
          child:
              TabBarView(controller: _tabController, children: tabbarViewList))
    ]);
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
            unselectedLabelColor: Colors.black54, // 未选中颜色
            controller: _tabController,
            tabs: tabs,
            isScrollable: true,
            indicatorWeight: 0.1);
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

  // 点击文字触发
  void onChange() {}
}
