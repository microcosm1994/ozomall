import 'package:flutter/material.dart';

class Login extends StatefulWidget {
  Login({Key key}) : super(key: key);

  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          elevation: 0,
          backgroundColor: Colors.white,
          leading: FlatButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Icon(
                Icons.close,
                color: Colors.black54,
              )),
        ),
        body: Container(
            color: Colors.white,
            width: double.infinity,
            height: double.infinity,
            child: Stack(
              children: [
                Column(
                  children: [
                    Container(
                        width: double.infinity,
                        padding: EdgeInsets.symmetric(vertical: 50),
                        child: Text(
                          "登录后继续操作",
                          style: TextStyle(color: Colors.black87, fontSize: 20),
                          textAlign: TextAlign.center,
                        )),
                    Container(
                        width: double.infinity,
                        padding: EdgeInsets.symmetric(vertical: 30),
                        child: Text(
                          "186****7892",
                          style: TextStyle(color: Colors.black, fontSize: 18),
                          textAlign: TextAlign.center,
                        )),
                    Container(
                        width: 300,
                        height: 40,
                        child: RaisedButton(
                          color: Colors.black87,
                          onPressed: () {},
                          child: Text("本机号码一键登录",
                              style: TextStyle(color: Colors.white)),
                        )),
                    Container(
                        width: 300,
                        height: 40,
                        margin: EdgeInsets.only(top: 20),
                        child: RaisedButton(
                          color: Colors.white,
                          onPressed: () {
                            Navigator.pushNamed(context, "/otherPhone");
                          },
                          child: Text("其他手机号码登录",
                              style: TextStyle(color: Colors.black87)),
                        )),
                    Container(
                        width: double.infinity,
                        padding:
                            EdgeInsets.symmetric(vertical: 30, horizontal: 40),
                        child: Text.rich(
                          TextSpan(text: "登陆表示您已阅读并同意", children: [
                            WidgetSpan(
                                child: Text("用户协议、",
                                    style: TextStyle(
                                        color: Colors.black87, fontSize: 12))),
                            WidgetSpan(
                                child: Text("隐私政策",
                                    style: TextStyle(
                                        color: Colors.black87, fontSize: 12))),
                            WidgetSpan(
                                child: Text("和中国联通认证服务协议",
                                    style: TextStyle(
                                        color: Colors.black87, fontSize: 12)))
                          ]),
                          style: TextStyle(color: Colors.black38, fontSize: 12),
                          textAlign: TextAlign.center,
                        )),
                  ],
                ),
                Align(
                    alignment: Alignment.bottomCenter,
                    child: FlatButton(
                        onPressed: () {},
                        child: Text("其他方式登录",
                            style: TextStyle(
                                color: Colors.black87, fontSize: 12)))),
              ],
              alignment: Alignment.center,
            )));
  }
}
