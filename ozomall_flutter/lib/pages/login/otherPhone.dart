import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:loading_indicator_view/loading_indicator_view.dart';
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
  int timeNum = 60;
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
    setState(() {
      timeNum = 59;
    });
    UserApi.sendMessage(data).then((res) {
      if (res["code"] == 1) {
        EasyLoading.showToast("验证码发送成功");
        var self = this;
        this.setState(() {
          timer = new Timer.periodic(new Duration(seconds: 1), (timer) {
            self.setState(() {
              if (timeNum > 0) {
                timeStr = '${timeNum--}s后重新获取';
              } else {
                timeStr = '获取验证码';
                timeNum = 60;
                timer.cancel();
                timer = null;
              }
            });
          });
        });
      } else {
        EasyLoading.showToast("验证码发送失败，请稍后再试。");
        // 验证码接口错误
        setState(() {
          timeStr = '获取验证码';
          timeNum = 60;
          if (timer != null) {
            timer.cancel();
            setState(() {
              timer = null;
            });
          }
        });
      }
    }).catchError((err) {
      EasyLoading.showError("验证码发送失败，请稍后再试。");
    });
  }

  // 登录
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
    EasyLoading.show(status: "正在登录...");
    data["phone"] = phoneController.text;
    data["code"] = codeController.text;
    UserApi.phoneLogin(data).then((res) {
      EasyLoading.dismiss();
      if (res["code"] == 1) {
        // 保存token
        UserUtils.setToken(res["data"]["token"]);
        // 保存用户信息
        UserUtils.setUserInfo(res["data"]["users"]);
        // 获取用户设置
        UserUtils.getSettings();
        // 关闭登陆页
        navigatorKey.currentState.pop();
      }
    }).catchError((err) {
      EasyLoading.dismiss();
      EasyLoading.showError("登陆失败，请稍后再试。");
    });
  }

  @override
  void initState() {
    // 监听手机号输入
    phoneController.addListener(() {
      this.setState(() {
        matched = exp.hasMatch(phoneController.text);
      });
    });
    // 监听验证码输入
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
                Navigator.pushReplacementNamed(context, "/login");
              },
              child: Icon(
                Icons.arrow_back_ios,
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
                                  enabledBorder: UnderlineInputBorder(
                                    borderSide:
                                        BorderSide(color: Colors.black26),
                                  ),
                                  focusedBorder: UnderlineInputBorder(
                                    borderSide:
                                        BorderSide(color: Colors.black54),
                                  ),
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
                                enabledBorder: UnderlineInputBorder(
                                  borderSide: BorderSide(color: Colors.black26),
                                ),
                                focusedBorder: UnderlineInputBorder(
                                  borderSide: BorderSide(color: Colors.black54),
                                ),
                                hintText: "验证码",
                                errorText: codeMatchedShow ? "请输入验证码" : null,
                                suffixStyle: TextStyle(color: Colors.black87),
                                suffix: GestureDetector(
                                    onTap: () {
                                      if (!matched || timeNum < 60) {
                                        return false;
                                      }
                                      sendMessage();
                                    },
                                    child: timeNum == 59
                                        ? LineSpinFadeLoaderIndicator(
                                            maxLineHeight: 4,
                                            maxLineWidth: 2,
                                            radius: 8,
                                            ballColor: Colors.black54,
                                          )
                                        : Text(
                                            timeStr,
                                            style: TextStyle(
                                                color: timeNum == 60
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
