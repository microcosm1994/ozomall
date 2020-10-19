import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/addressApi.dart';

class Address extends StatefulWidget {
  Address({Key key}) : super(key: key);

  @override
  _AddressState createState() => _AddressState();
}

class _AddressState extends State<Address> {
  List addressList = [];

  // 获取地址
  void getAddress() {
    AddressApi.getAddress({"id": 4}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          addressList = res["data"];
        });
        print(addressList);
      }
    });
  }

  @override
  void initState() {
    getAddress();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            elevation: 0,
            backgroundColor: Colors.white,
            title: Text("地址管理", style: TextStyle(color: Colors.black87)),
            centerTitle: true,
            leading: FlatButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                child: Icon(
                  Icons.arrow_back_ios,
                  color: Colors.black54,
                ))),
        bottomNavigationBar: Container(
            margin: EdgeInsets.only(top: 2),
            padding: EdgeInsets.symmetric(horizontal: 10),
            width: double.infinity,
            child: FlatButton(
                color: Color(0xff56C0C1),
                textColor: Colors.white,
                child: Text("添加新地址"),
                onPressed: () {})),
        body: ListView(children: buildList(context)));
  }

  // 渲染列表
  List<Widget> buildList(BuildContext context) {
    List<Widget> list = [];
    for (var i = 0; i < addressList.length; i++) {
      Widget item = Container(
          padding: EdgeInsets.symmetric(vertical: 4, horizontal: 10),
          decoration: BoxDecoration(
              color: Colors.white,
              border: new Border(
                  bottom: BorderSide(color: Color(0xf5f5f5f5), width: 1))),
          width: double.infinity,
          child: Row(children: [
            Expanded(
              child: Column(children: [
                Container(
                    height: 30,
                    child: Row(
                      children: [
                        Container(
                            padding: EdgeInsets.symmetric(horizontal: 4),
                            color: Colors.black38,
                            height: 20,
                            child: Text(
                              "默认",
                              style: TextStyle(
                                  height: 1.5,
                                  color: Colors.white,
                                  fontSize: 12),
                            )),
                        Container(
                            margin: EdgeInsets.only(left: 10),
                            height: 20,
                            child: Text(
                              addressList[i],
                              style: TextStyle(
                                  height: 1.5,
                                  color: Colors.black87,
                                  fontSize: 14),
                            ))
                      ],
                    )),
                Container(
                    height: 30,
                    child: Row(
                      children: [
                        Container(
                            padding: EdgeInsets.symmetric(horizontal: 4),
                            height: 20,
                            child: Text(
                              "杜波",
                              style: TextStyle(
                                  height: 1.5,
                                  color: Colors.black54,
                                  fontSize: 12),
                            )),
                        Container(
                            margin: EdgeInsets.only(left: 10),
                            height: 20,
                            child: Text(
                              "18647327892",
                              style: TextStyle(
                                  height: 1.5,
                                  color: Colors.black54,
                                  fontSize: 12),
                            ))
                      ],
                    ))
              ]),
            ),
            Container(
                width: 50,
                child: FlatButton(
                  child: Icon(
                    Icons.mode_edit,
                    color: Colors.black54,
                    size: 20,
                  ),
                  onPressed: () {},
                ))
          ]));
      list.add(item);
    }
    return list;
  }
}
