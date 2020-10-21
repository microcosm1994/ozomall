import 'dart:async';

import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/userApi.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/utils/UserUtils.dart';

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
  int timeNum = 59;
  String timeStr = "获取验证码";
  // 手机号验证结果
  bool matched = false;
  // 验证码验证结果
  bool codeMatched = false;
  bool codeMatchedShow = false;
  // 验证手机号正则表达式
  RegExp exp = RegExp(
      r'^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\d{8}$');
  // 发送短信验证码
  void sendMessage() {
    Map<String, String> data = new Map();
    data["phone"] = phoneController.text;
    UserApi.sendMessage(data).then((res) {
      print(res);
      if (res["code"] == 1) {
        var self = this;
        print(res);
        this.setState(() {
          timer = new Timer.periodic(new Duration(seconds: 1), (timer) {
            print(timer);
            self.setState(() {
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

  void login() {
    Map<String, String> data = new Map();
    if (!matched) {
      return;
    }
    if (codeMatched) {
      this.setState(() {
        codeMatchedShow = true;
      });
      return;
    }
    data["phone"] = phoneController.text;
    data["code"] = codeController.text;
    UserApi.phoneLogin(data).then((res) {
      if (res["code"] == 1) {
        // 保存token
        UserUtils.setToken(res["data"]["token"]);
        // 保存用户信息
        UserUtils.setUserInfo(res["data"]["users"]);
        // 关闭登陆页
        navigatorKey.currentState.pop();
      }
    });
  }

  @override
  void initState() {
    phoneController.addListener(() {
      this.setState(() {
        matched = exp.hasMatch(phoneController.text);
      });
    });
    codeController.addListener(() {
      this.setState(() {
        codeMatched = codeController.text.isEmpty;
        codeMatchedShow = false;
      });
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
                navigatorKey.currentState.pop();
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
                            height: 60,
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
                            height: 60,
                            child: TextFormField(
                              decoration: InputDecoration(
                                hintText: "验证码",
                                errorText: codeMatchedShow ? "请输入验证码" : null,
                                suffixStyle: TextStyle(color: Colors.black87),
                                suffix: GestureDetector(
                                    onTap: () {
                                      if (!matched || timeNum < 59) {
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
                              onPressed: login, // 登录按钮点击事件
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
