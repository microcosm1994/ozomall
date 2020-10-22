import 'package:flutter/material.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/utils/UserUtils.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';

class MyPage extends StatefulWidget {
  MyPage({Key key}) : super(key: key);

  @override
  _MyPageState createState() => _MyPageState();
}

class _MyPageState extends State<MyPage>
    with AutomaticKeepAliveClientMixin, RouteAware {
  Map userInfo;
  // 获取用户信息
  void getUserInfo() async {
    var data = await UserUtils.getUserInfo();
    setState(() {
      userInfo = data;
    });
  }

  @override
  void initState() {
    getUserInfo();
    // TODO: implement initState
    super.initState();
  }

  @override
  void didChangeDependencies() {
    // 订阅路由监听
    routeObserver.subscribe(this, ModalRoute.of(context));
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
  }

  @override
  void didPopNext() {
    getUserInfo();
    // TODO: implement didPopNext
    super.didPopNext();
  }

  @override
  void didPushNext() {
    // TODO: implement didPushNext
    super.didPushNext();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildAppBar(),
      backgroundColor: Color(0xf5f5f5f5),
      body: ListView(
        children: [
          buildUserInfoCard(userInfo), // 用户信息卡片
          buildUserCount(), // 用户数据统计
          Container(
            margin: EdgeInsets.only(top: 2),
            child: Row(
              children: [
                Expanded(
                    child: Cell(
                  title: "收藏",
                  describe: "11",
                  isArrow: true,
                )),
                Expanded(
                    child: Cell(
                  title: "足迹",
                  describe: "11",
                  isArrow: true,
                ))
              ],
            ),
          ),
          Cell(
              title: "购买",
              describe: "10",
              isArrow: true,
              child: Text("111111")),
          Cell(title: "钱包", isArrow: true, child: Text("111111")),
          Cell(
            title: "申请成为卖家",
            describe: "回款快速，安全仓储",
            isArrow: true,
          ),
        ],
      ),
    );
  }

  // AppBar
  AppBar buildAppBar() {
    return AppBar(
      backgroundColor: Colors.white,
      elevation: 0,
      leading: IconButton(
          icon: Icon(
            Icons.crop_free,
            color: Colors.black54,
          ),
          onPressed: () {
            print(1);
          }),
      actions: [
        IconButton(
            icon: Icon(
              Icons.notifications_none,
              color: Colors.black54,
            ),
            onPressed: () {
              print(1);
            }),
        IconButton(
            icon: Icon(
              Icons.settings,
              color: Colors.black54,
            ),
            onPressed: () {
              Navigator.pushNamed(context, "/setting");
            })
      ],
    );
  }

  // 用户信息卡片
  Widget buildUserInfoCard(Map userInfo) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 10),
      width: double.infinity,
      height: 70,
      color: Colors.white,
      child: Row(children: [
        Container(
            width: 70,
            height: 70,
            padding: EdgeInsets.all(5),
            child: new ClipOval(
              child: new Image.network(userInfo == null
                  ? "https://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/avator/default.jpg"
                  : userInfo["avatarUrl"]),
            )),
        Expanded(
            child: Column(
          children: [
            Row(
              children: [
                Expanded(
                    child: Text(userInfo == null ? "用户名" : userInfo["nickName"],
                        style: TextStyle(fontSize: 16))),
                Container(
                  width: 50,
                  height: 50,
                  child: Icon(
                    Icons.navigate_next,
                    color: Colors.black45,
                  ),
                )
              ],
            ),
            Container(
              width: double.infinity,
              child: userInfo == null
                  ? Text("")
                  : Text(
                      userInfo["sign"] == null ? "未设置签名" : userInfo["sign"],
                      style: TextStyle(color: Colors.black45, fontSize: 12),
                      textAlign: TextAlign.left,
                    ),
            )
          ],
        ))
      ]),
    );
  }

  // 用户数据统计
  Widget buildUserCount() {
    return Container(
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
        height: 50,
        width: double.infinity,
        color: Colors.white,
        child: Row(
          children: [
            Expanded(
              child: Column(
                children: [
                  Text(
                    "0",
                    style: TextStyle(color: Colors.black87, fontSize: 16),
                  ),
                  Text(
                    "被喜欢",
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )
                ],
              ),
            ),
            Expanded(
              child: Column(
                children: [
                  Text(
                    "0",
                    style: TextStyle(color: Colors.black87, fontSize: 16),
                  ),
                  Text(
                    "粉丝",
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )
                ],
              ),
            ),
            Expanded(
              child: Column(
                children: [
                  Text(
                    "0",
                    style: TextStyle(color: Colors.black87, fontSize: 16),
                  ),
                  Text(
                    "关注",
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )
                ],
              ),
            ),
            Expanded(
              child: Column(
                children: [
                  Text(
                    "0",
                    style: TextStyle(color: Colors.black87, fontSize: 16),
                  ),
                  Text(
                    "动态",
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )
                ],
              ),
            ),
          ],
        ));
  }

  @override
  // TODO: implement wantKeepAlive
  bool get wantKeepAlive => true;
}
