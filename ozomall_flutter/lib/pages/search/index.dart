import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/search.dart';
import 'package:ozomall_flutter/widget/staggeredListView/index.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Search extends StatefulWidget {
  Search({Key key}) : super(key: key);

  @override
  _SearchState createState() => _SearchState();
}

class _SearchState extends State<Search> {
  String searchValue = ""; // 搜索内容
  int currentPageIndex = 0; // 当前显示页面
  final controller = TextEditingController(); // 输入框控制器
  int toolBarIndex = 0; // 搜索结果页排序工具栏选中
  int iconIndex = 0; // 价格排序图标箭头高亮
  List goodsList; // 商品列表
  List searchHistoryList; // 商品列表
  // 分页信息
  Map page = {"page": 1, "size": 10};

  // 获取历史搜索记录
  void getSearchHistoryList() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    this.setState(() {
      searchHistoryList = prefs.getStringList("searchHistoryList");
    });
    print(searchHistoryList);
  }

  // 添加历史搜索记录
  void setSearchHistoryList(val) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String> list = await prefs.getStringList("searchHistoryList");
    if (list == null) {
      list = <String>[];
    }
    list.remove(val); // 先删除list中同样的值，确保没有重复的词
    list.insert(0, val); // 添加到历史搜索记录中
    if (list.length > 10) {
      list.removeRange(10, list.length);
    }
    prefs.setStringList("searchHistoryList", list);
  }

  // 清空历史搜索记录
  void clearSearchHistoryList() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setStringList("searchHistoryList", []);
    print(searchHistoryList);
  }

  // 点击搜索商品
  void search(val) {
    // 添加到历史搜索记录中
    setSearchHistoryList(val);
    //点击提交搜索商品
    SearchApi.search({
      "goodsName": val,
      "page": page["page"],
      "size": page["size"],
    }).then((res) {
      print(res);
      if (res["code"] == 1) {
        this.setState(() {
          currentPageIndex = 2;
          goodsList = res["data"]["records"];
        });
      }
    });
  }

  @override
  void initState() {
    // 绑定值
    controller.text = searchValue;
    // 监听输入框输入
    controller.addListener(() {
      if (controller.text.isEmpty) {
        getSearchHistoryList();
        this.setState(() {
          currentPageIndex = 0;
        });
      } else {
        this.setState(() {
          currentPageIndex = 1;
        });
      }
    });
    getSearchHistoryList();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(70),
        child: buildAppBar(),
      ),
      body: IndexedStack(index: currentPageIndex, children: [
        buildSearchIndexPage(), // 搜索页首页
        buildSearchDists(), // 搜索词列表
        buildSearchResult() // 搜索结果
      ]),
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
                    "取消",
                    style: TextStyle(color: Colors.black87),
                  ),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                )))
      ],
      automaticallyImplyLeading: false,
      leading: null,
      backgroundColor: Colors.white,
      elevation: 0, // 消除底部阴影
      title: Container(
        width: double.infinity,
        height: 30,
        child: buildTextField(),
      ),
      bottom: PreferredSize(
          child: currentPageIndex == 2 ? buildToolBar() : Text(""),
          preferredSize: Size.fromHeight(30)),
    );
  }

  // TextField
  TextField buildTextField() {
    return TextField(
      maxLines: 1, //最大行数
      autocorrect: false, //是否自动更正
      style:
          TextStyle(fontSize: 14.0, color: Colors.black87, height: 1), //输入文本的样式
      decoration: InputDecoration(
          fillColor: Colors.grey[200],
          filled: true,
          disabledBorder: InputBorder.none,
          enabledBorder: InputBorder.none,
          focusedBorder: InputBorder.none,
          hintText: '输入搜索内容',
          // 输入框前面的小部件
          prefixIcon: Icon(
            Icons.search,
            color: Colors.black54,
          )),
      onSubmitted: search,
      controller: controller,
    );
  }

  // 搜索页首页
  Widget buildSearchIndexPage() {
    return Column(children: [
      Container(
          constraints: BoxConstraints(maxHeight: 120),
          child: searchHistoryList == null || searchHistoryList.length == 0
              ? Text("")
              : buildSearchBox(
                  "历史纪录",
                  FlatButton(
                    onPressed: () {
                      clearSearchHistoryList(); // 清空历史搜索记录
                      getSearchHistoryList(); // 获取历史搜索记录
                    },
                    child: Icon(
                      Icons.delete,
                      size: 16,
                      color: Colors.black54,
                    ),
                  ),
                  searchHistoryList)),
      Expanded(
          child: Container(
        child: buildSearchBox(
            "热门搜索",
            Icon(
              Icons.refresh,
              size: 16,
              color: Colors.black54,
            ),
            [
              "11",
              "22",
              "222",
              "333",
              "11",
              "22",
              "222",
              "333",
            ]),
        width: double.infinity,
      )),
    ]);
  }

  // 搜索标签
  Widget buildSearchBox(String title, Widget icons, List tags) {
    List<Widget> chipList = [];
    for (var i = 0; i < tags.length; i++) {
      Widget chip = ActionChip(
        label: Text(
          tags[i],
          style: TextStyle(fontSize: 12, color: Colors.black54),
        ),
        backgroundColor: Colors.grey[200],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(2.0),
        ),
        onPressed: () {
          // 点击标签搜索商品
          this.setState(() {
            controller.text = tags[i];
            currentPageIndex = 2;
          });
          search(tags[i]);
        },
      );
      chipList.add(chip);
    }
    return Column(
      children: [
        Container(
            padding: EdgeInsets.symmetric(horizontal: 20),
            width: double.infinity,
            child: Column(children: [
              Container(
                  height: 30,
                  width: double.infinity,
                  child: Row(children: [
                    Expanded(
                        child: Text(title,
                            textAlign: TextAlign.left,
                            style: TextStyle(
                                color: Colors.black87, fontSize: 14))),
                    Expanded(
                        child: Text.rich(
                      TextSpan(children: [WidgetSpan(child: icons)]),
                      textAlign: TextAlign.right,
                    ))
                  ]))
            ])),
        Expanded(
            child: Container(
          padding: EdgeInsets.symmetric(horizontal: 20),
          width: double.infinity,
          child: Wrap(
            spacing: 8.0,
            runSpacing: 0.0,
            children: chipList,
          ),
        ))
      ],
    );
  }

  // 搜索词列表页
  Widget buildSearchDists() {
    return ListView.separated(
      shrinkWrap: false,
      padding: EdgeInsets.symmetric(horizontal: 20),
      itemCount: 20,
      itemBuilder: (BuildContext context, int index) {
        return new Container(height: 30, child: Text(index.toString()));
      },
      separatorBuilder: (BuildContext context, int index) {
        return new Container(height: 1.0, color: Color(0xf5f5f5f5));
      },
    );
  }

  // 搜索结果排序工具栏
  Widget buildToolBar() {
    return Container(
        height: 30,
        width: double.infinity,
        child: Row(
          children: [
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 0;
                      });
                    },
                    child: Text("综合",
                        style: TextStyle(
                            color: toolBarIndex == 0
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 1;
                      });
                    },
                    child: Text("销量",
                        style: TextStyle(
                            color: toolBarIndex == 1
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            FlatButton(
                onPressed: () {
                  this.setState(() {
                    toolBarIndex = 2;
                    print(toolBarIndex);
                    if (toolBarIndex == 2) {
                      iconIndex = iconIndex == 0 ? 1 : 0;
                    }
                  });
                },
                child: Text.rich(
                  TextSpan(text: "价格", children: [
                    WidgetSpan(
                        child: Container(
                            height: 20,
                            child: Column(
                              children: [
                                Container(
                                  height: 10,
                                  child: Icon(Icons.expand_less,
                                      size: 16,
                                      color: toolBarIndex == 2 && iconIndex == 0
                                          ? Color(0xff56C0C1)
                                          : Colors.black54),
                                ),
                                Container(
                                  height: 10,
                                  child: Icon(Icons.expand_more,
                                      size: 16,
                                      color: toolBarIndex == 2 && iconIndex == 1
                                          ? Color(0xff56C0C1)
                                          : Colors.black54),
                                )
                              ],
                            )))
                  ]),
                  style: TextStyle(
                      color: toolBarIndex == 2
                          ? Color(0xff56C0C1)
                          : Colors.black54),
                )),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 3;
                      });
                    },
                    child: Text("新品",
                        style: TextStyle(
                            color: toolBarIndex == 3
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 4;
                      });
                    },
                    child: Text("筛选",
                        style: TextStyle(
                            color: toolBarIndex == 4
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
          ],
        ));
  }

  // 搜索结果页
  Widget buildSearchResult() {
    return ListView(children: [StaggeredListView(list: goodsList)]);
  }
}
