import 'dart:async';

import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/userApi.dart';

class OtherPhone extends StatefulWidget {
  OtherPhone({Key key}) : super(key: key);

  @override
  _OtherPhoneState createState() => _OtherPhoneState();
}

class _OtherPhoneState extends State<OtherPhone> {
  final phoneController = TextEditingController();
  final codeController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  // 验证码倒计时
  Timer timer;
  int timeNum = 0;
  String timeStr = "获取验证码";
  // 手机号验证结果
  bool matched = false;
  // 验证手机号正则表达式
  RegExp exp = RegExp(
      r'^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\d{8}$');
  // 发送手机号
  void sendMessage() {
    Map<String, String> data = new Map();
    data["phone"] = phoneController.text;
    UserApi.sendMessage(data).then((res) {
      print(res);
      if (res["code"] == 1) {
        this.setState(() {
          timer = new Timer.periodic(new Duration(seconds: 1), (timer) {
            setState(() {
              if (timeNum > 0) {
                timeStr = '${timeNum--}重新获取';
              } else {
                timeStr = '获取验证码';
                timeNum = 59;
                timer.cancel();
                timer = null;
              }
            });
          });
        });
      }
    });
  }

  @override
  void initState() {
    phoneController.addListener(() {
      this.setState(() {
        matched = exp.hasMatch(phoneController.text);
      });
      print('input ${phoneController.text}');
      print(matched);
    });
    codeController.addListener(() {
      print('input ${codeController.text}');
    });
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.white,
        resizeToAvoidBottomPadding: false, //输入框抵住键盘
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
            child: Form(
                key: _formKey,
                child: Stack(
                  children: [
                    Column(
                      children: [
                        Container(
                            width: double.infinity,
                            padding: EdgeInsets.symmetric(vertical: 50),
                            child: Text(
                              "登录后继续操作",
                              style: TextStyle(
                                  color: Colors.black87, fontSize: 20),
                              textAlign: TextAlign.center,
                            )),
                        Container(
                            width: 300,
                            height: 50,
                            child: TextFormField(
                                keyboardType: TextInputType.phone, //手机号
                                decoration: InputDecoration(
                                  hintText: "输入手机号",
                                  errorText:
                                      matched || phoneController.text.isEmpty
                                          ? null
                                          : "请输入正确的手机号",
                                ),
                                controller: phoneController)),
                        Container(
                            width: 300,
                            height: 50,
                            child: TextFormField(
                              decoration: InputDecoration(
                                hintText: "验证码",
                                suffixStyle: TextStyle(color: Colors.black87),
                                suffix: GestureDetector(
                                    onTap: () {
                                      if (!matched || timeNum > 0) {
                                        return false;
                                      }
                                      sendMessage();
                                    },
                                    child: Text(
                                      timeStr,
                                      style: TextStyle(
                                          color: matched
                                              ? Colors.black87
                                              : Colors.black54),
                                    )),
                              ),
                              controller: codeController,
                            )),
                        Container(
                            width: 300,
                            height: 40,
                            margin: EdgeInsets.only(top: 20),
                            child: RaisedButton(
                              color: Colors.black87,
                              onPressed: () {
                                print(phoneController.text);
                              },
                              child: Text("登录",
                                  style: TextStyle(color: Colors.white)),
                            )),
                        Container(
                            width: double.infinity,
                            padding: EdgeInsets.symmetric(
                                vertical: 30, horizontal: 40),
                            child: Text.rich(
                              TextSpan(text: "登陆表示您已阅读并同意", children: [
                                WidgetSpan(
                                    child: Text("用户协议、",
                                        style: TextStyle(
                                            color: Colors.black87,
                                            fontSize: 12))),
                                WidgetSpan(
                                    child: Text("隐私政策",
                                        style: TextStyle(
                                            color: Colors.black87,
                                            fontSize: 12))),
                              ]),
                              style: TextStyle(
                                  color: Colors.black38, fontSize: 12),
                              textAlign: TextAlign.center,
                            )),
                      ],
                    ),
                  ],
                  alignment: Alignment.center,
                ))));
  }

  // 不要忘记在这里释放掉Timer
  @override
  void dispose() {
    timer?.cancel();
    timer = null;
    super.dispose();
  }
}
