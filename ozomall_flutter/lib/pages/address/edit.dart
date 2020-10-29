import 'package:city_pickers/city_pickers.dart';
import 'package:city_pickers/modal/result.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:ozomall_flutter/api/addressApi.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/utils/userUtils.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';

class AddressEdit extends StatefulWidget {
  AddressEdit(
      {Key key, @required this.title, this.addressData, this.isDefault = false})
      : super(key: key);
  final String title;
  final Map addressData;
  final bool isDefault; // 是否为默认地址
  // (这页面有俩个默认地址参数，一个是原来是否为默认地址，另一个是要不要设置为默认地址)

  @override
  _AddressEditState createState() => _AddressEditState();
}

class _AddressEditState extends State<AddressEdit> {
  final nameController = TextEditingController(); // 收货人
  final phoneController = TextEditingController(); // 手机号
  final addressController = TextEditingController(); // 详细地址
  String region = ""; // 城市/区域
  String areaCode = ""; // 区域编号
  // 手机号验证结果
  bool matched = false;
  // 验证手机号正则表达式
  RegExp exp = RegExp(
      r'^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\d{8}$');
  bool isSetDefault = false; // 是否设置为默认地址

  // 表单验证
  bool formVerify() {
    if (nameController.text.isEmpty) {
      EasyLoading.showError("请输入收货人姓名");
      return false;
    }
    if (phoneController.text.isEmpty) {
      EasyLoading.showError("请输入手机号");
      return false;
    }
    if (!matched) {
      EasyLoading.showError("请输入正确的手机号");
      return false;
    }
    if (region.isEmpty) {
      EasyLoading.showError("请选择城市区域");
      return false;
    }
    if (addressController.text.isEmpty) {
      EasyLoading.showError("请输入详细地址");
      return false;
    }
    return true;
  }

  // 添加新地址
  void addAddress() async {
    // 验证表单
    bool flag = formVerify();
    if (!flag) return;
    // 获取userId
    var users = await UserUtils.getUserInfo();
    Map data = new Map();
    data["userId"] = users["id"]; // userId
    data["consignee"] = nameController.text; // 收货人
    data["region"] = region; // 城市/区域
    data["address"] = addressController.text; // 详细地址
    data["phone"] = phoneController.text; // 手机号
    data["areaCode"] = areaCode; // 区域编码
    AddressApi.addAddress(data).then((res) {
      if (res["code"] == 1) {
        // 重新设置默认地址（之前不是默认地址，设置为默认地址）
        if (!widget.isDefault && isSetDefault) {
          setDefault(res["data"]["id"]);
        }
        // 取消默认地址（之前是默认地址，取消默认地址）
        if (widget.isDefault && !isSetDefault) {
          setDefault(0);
        }
        // 返回地址列表页
        navigatorKey.currentState.pop();
      }
    });
  }

  // 修改地址
  void putAddress(int id) async {
    // 验证表单
    bool flag = formVerify();
    if (!flag) return;
    // 获取userId
    var users = await UserUtils.getUserInfo();
    Map data = new Map();
    data["id"] = id; // id
    data["userId"] = users["id"]; // userId
    data["consignee"] = nameController.text; // 收货人
    data["region"] = region; // 城市/区域
    data["address"] = addressController.text; // 详细地址
    data["phone"] = phoneController.text; // 手机号
    data["areaCode"] = areaCode; // 区域编码
    AddressApi.putAddress(data).then((res) {
      print(res);
      if (res["code"] == 1) {
        // 重新设置默认地址（之前不是默认地址，设置为默认地址）
        if (!widget.isDefault && isSetDefault) {
          setDefault(res["data"]["id"]);
        }
        // 取消默认地址（之前是默认地址，取消默认地址）
        if (widget.isDefault && !isSetDefault) {
          setDefault(0);
        }
        // 返回地址列表页
        navigatorKey.currentState.pop();
      }
    });
  }

  // 设置默认地址
  void setDefault(int id) async {
    // 获取用户默认设置
    var userSettings = await UserUtils.getUserSettings();
    Map setData = new Map();
    setData["id"] = userSettings["id"];
    setData["addressId"] = id;
    UserUtils.setSettings(setData);
  }

  @override
  void initState() {
    // 如果是编辑的话给输入框赋值
    if (widget.addressData != null) {
      nameController.text = widget.addressData["consignee"];
      phoneController.text = widget.addressData["phone"];
      region = widget.addressData["region"];
      addressController.text = widget.addressData["address"];
      // 设置默认值
      if (widget.isDefault) {
        isSetDefault = widget.isDefault;
      }
      matched = true;
    }
    // 监听姓名输入
    nameController.addListener(() {
      print(nameController.text);
    });
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
      resizeToAvoidBottomPadding: false, //输入框抵住键盘
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: AppBar(
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
                onTap: () {
                  if (widget.addressData == null) {
                    addAddress();
                  } else {
                    putAddress(widget.addressData["id"]);
                  }
                },
                child: Container(
                    width: 60,
                    height: double.infinity,
                    padding: EdgeInsets.symmetric(vertical: 15),
                    child: Text("保存",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            fontSize: 14, color: Colors.black87, height: 1.5))))
          ],
        ),
      ),
      body: Column(children: [
        Container(
            width: double.infinity,
            height: 50,
            color: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: TextFormField(
                decoration: InputDecoration(
                  enabledBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.black26),
                  ),
                  focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.black54),
                  ),
                  hintText: "姓名",
                  hintStyle: TextStyle(fontSize: 14),
                ),
                controller: nameController)),
        Container(
            width: double.infinity,
            height: 60,
            color: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: TextFormField(
                keyboardType: TextInputType.phone, //数字键盘
                decoration: InputDecoration(
                  enabledBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.black26),
                  ),
                  focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.black54),
                  ),
                  hintText: "手机号",
                  hintStyle: TextStyle(fontSize: 14),
                  errorText: matched || phoneController.text.isEmpty
                      ? null
                      : "请输入正确的手机号",
                ),
                controller: phoneController)),
        Container(
            width: double.infinity,
            color: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 10),
            child: Cell(
              isArrow: true,
              title: region.isEmpty ? "城市/区域" : region,
              childHeight: 70,
              child: Container(
                  width: double.infinity,
                  height: double.infinity,
                  child: TextFormField(
                      maxLines: 2,
                      keyboardType: TextInputType.text,
                      decoration: InputDecoration(
                        enabledBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.black26),
                        ),
                        focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.black54),
                        ),
                        hintText: "详细地址",
                        hintStyle: TextStyle(fontSize: 12),
                      ),
                      controller: addressController)),
              onTap: () async {
                Result result = await CityPickers.showCityPicker(
                  context: context,
                  height: 260,
                );
                print(result);
                if (result != null) {
                  setState(() {
                    region = result.provinceName +
                        " " +
                        result.cityName +
                        " " +
                        result.areaName;
                    areaCode = result.areaId;
                  });
                }
              },
            )),
        Container(
            width: double.infinity,
            height: 50,
            margin: EdgeInsets.only(top: 4),
            padding: EdgeInsets.symmetric(horizontal: 20),
            color: Colors.white,
            child: Row(
              children: [
                Expanded(child: Text("设置为默认地址")),
                Container(
                    width: 60,
                    child: Switch(
                      onChanged: (bool value) {
                        setState(() {
                          isSetDefault = value;
                        });
                      },
                      value: isSetDefault,
                    ))
              ],
            )),
      ]),
    );
  }
}
