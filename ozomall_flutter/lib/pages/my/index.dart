import 'package:flutter/material.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';

class MyPage extends StatefulWidget {
  MyPage({Key key}) : super(key: key);

  @override
  _MyPageState createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> with AutomaticKeepAliveClientMixin {
  
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildAppBar(),
      backgroundColor: Color(0xf5f5f5f5),
      body: ListView(
        children: [
          buildUserInfoCard(), // 用户信息卡片
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
  Widget buildUserInfoCard() {
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
              child: new Image.network(
                  "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600682885342&di=2f4fa32c407785f7de732d4b55d7b1ca&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201509%2F18%2F20150918165700_nUeaj.jpeg"),
            )),
        Expanded(
            child: Column(
          children: [
            Row(
              children: [
                Expanded(child: Text("强悍黑钻", style: TextStyle(fontSize: 16))),
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
              child: Text(
                "未设置签名",
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
