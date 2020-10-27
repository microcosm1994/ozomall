import 'package:flutter/material.dart';

class AddressEdit extends StatefulWidget {
  AddressEdit({Key key, @required this.title}) : super(key: key);
  final String title;

  @override
  _AddressEditState createState() => _AddressEditState();
}

class _AddressEditState extends State<AddressEdit> {
  final phoneController = TextEditingController();
  // 手机号验证结果
  bool matched = false;
  // 验证手机号正则表达式
  RegExp exp = RegExp(
      r'^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\d{8}$');
  @override
  void initState() {
    // 监听手机号输入
    phoneController.addListener(() {
      this.setState(() {
        matched = exp.hasMatch(phoneController.text);
      });
    });
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.white,
        title: Text(widget.title, style: TextStyle(color: Colors.black87)),
        centerTitle: true,
        leading: FlatButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Icon(
              Icons.arrow_back_ios,
              color: Colors.black54,
            )),
        actions: [
          GestureDetector(
              onTap: () {},
              child: Container(
                  width: 60,
                  height: double.infinity,
                  padding: EdgeInsets.symmetric(vertical: 20),
                  child: Text("保存",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          fontSize: 14, color: Colors.black87, height: 1.5))))
        ],
      ),
      body: Column(children: [
        Container(
            width: 300,
            height: 60,
            child: TextFormField(
                keyboardType: TextInputType.phone, //手机号
                decoration: InputDecoration(
                  hintText: "输入手机号",
                  errorText: matched || phoneController.text.isEmpty
                      ? null
                      : "请输入正确的手机号",
                ),
                controller: phoneController)),
        Container(width: 300, height: 60, child: Text("")),
      ]),
    );
  }
}
