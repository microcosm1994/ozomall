import 'package:flutter/material.dart';
import 'package:ozomall_flutter/pages/indexPage/recommend.dart';

class IndexPage extends StatefulWidget {
  IndexPage({Key key}) : super(key: key);

  @override
  _IndexPageState createState() => _IndexPageState();
}

class _IndexPageState extends State<IndexPage> {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          leading: buildLeading(), // 搜索按钮
          backgroundColor: Colors.white, // 背景色
          centerTitle: true,
          title: buildTitle(), // 标题
          titleSpacing: 10.0,
          actions: [
            Padding(
                padding: EdgeInsets.only(right: 10.0),
                child: IconButton(
                  icon: Icon(
                    Icons.camera_alt,
                    color: Colors.black54,
                  ),
                  onPressed: () {
                    print(1);
                  },
                )),
          ],
          elevation: 0, // 消除底部阴影
        ), // appbar
        body: TabBarView(children: [
          Text("关注"), // 关注页面
          Recommend() // 推荐页面
        ]),
        backgroundColor: Color(0xf5f5f5f5), // 背景色
      ),
    );
  }

  // leading
  Widget buildLeading() {
    return Padding(
        padding: EdgeInsets.only(left: 10.0),
        child: IconButton(
          icon: Icon(
            Icons.search,
            color: Colors.black54,
          ),
          onPressed: () {
            print(1);
          },
        ));
  }

  // title
  Widget buildTitle() {
    return TabBar(
        labelColor: Colors.black87, // 选中颜色
        unselectedLabelColor: Colors.black54, // 未选中颜色
        tabs: [
          Tab(
            child: Text("关注", style: TextStyle(fontSize: 16)),
          ),
          Tab(
            child: Text("推荐", style: TextStyle(fontSize: 16)),
          )
        ],
        indicatorWeight: 0.1);
  }
}
