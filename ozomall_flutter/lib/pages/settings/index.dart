import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/userApi.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/utils/UserUtils.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';

class Setting extends StatefulWidget {
  Setting({Key key}) : super(key: key);

  @override
  _SettingState createState() => _SettingState();
}

class _SettingState extends State<Setting> {
  // 登出
  void logout() {
    UserApi.logout().then((res) {
      if (res["code"] == 1) {
        UserUtils.clearToken(); // 清除token
        UserUtils.clearUserInfo(); // 清除用户信息
        navigatorKey.currentState.pop("/"); // 跳转到购买页
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color(0xf5f5f5f5),
        appBar: AppBar(
            elevation: 0,
            backgroundColor: Colors.white,
            title: Text("设置", style: TextStyle(color: Colors.black87)),
            centerTitle: true,
            leading: FlatButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                child: Icon(
                  Icons.arrow_back_ios,
                  color: Colors.black54,
                ))),
        body: ListView(children: [
          Column(
            children: buildList(context),
          ),
          Container(
              margin: EdgeInsets.only(top: 2),
              color: Colors.white,
              width: double.infinity,
              child: FlatButton(
                  color: Colors.white,
                  textColor: Colors.black54,
                  child: Text("退出登录"),
                  onPressed: logout)),
        ]));
  }

  // 渲染列表
  List<Widget> buildList(BuildContext context) {
    List<Widget> list = [];
    List data = [
      {"title": "账号安全", "route": ""},
      {"title": "支付设置", "route": ""},
      {"title": "身份认证", "route": ""},
      {"title": "地址管理", "route": "/address"},
      {"title": "我的尺码", "route": ""},
      {"title": "隐私设置", "route": ""},
      {"title": "消息设置", "route": ""},
      {"title": "通用设置", "route": ""},
      {"title": "反馈与建议", "route": ""},
      {"title": "关于OZOMALL", "route": ""}
    ];
    for (var i = 0; i < data.length; i++) {
      Widget item = Container(
          width: double.infinity,
          child: Cell(
            isArrow: true,
            title: data[i]["title"],
            onTap: () {
              Navigator.pushNamed(context, data[i]["route"]);
            },
          ));
      list.add(item);
    }
    return list;
  }
}
