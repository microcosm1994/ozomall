import 'package:flutter/material.dart';
import 'package:ozomall_flutter/utils/userUtils.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Store extends StatefulWidget {
  Store({Key key}) : super(key: key);

  @override
  _StoreState createState() => _StoreState();
}

class _StoreState extends State<Store> {
  List<Map> data = [];
  // 获取全部数据
  void getData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    Set<String> keys = prefs.getKeys();
    data.clear();
    keys.forEach((key) {
      Map dataVal = new Map();
      var val = prefs.get(key);
      dataVal["key"] = key;
      dataVal["val"] = val;
      this.setState(() {
        data.add(dataVal);
      });
    });
    return;
  }

  @override
  void initState() {
    getData();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          title: Text("data数据"),
          leading: FlatButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Icon(
                Icons.arrow_back_ios,
                color: Colors.black54,
              )),
          bottom: PreferredSize(
            child: Container(
                child: Row(children: [
              Expanded(
                  child: Text.rich(TextSpan(
                      text: "length:",
                      style: TextStyle(color: Colors.black54, height: 1.5),
                      children: [
                    WidgetSpan(
                        child: Text(
                      data.length.toString(),
                      style: TextStyle(color: Colors.black87),
                    ))
                  ])))
            ])),
            preferredSize: Size.fromHeight(30),
          ),
        ),
        body: Container(
          child: ListView.builder(
            itemCount: data.length,
            itemBuilder: (BuildContext context, int index) {
              return Container(
                  decoration: BoxDecoration(
                      border: Border(
                          bottom: BorderSide(color: Colors.black54, width: 1))),
                  width: double.infinity,
                  padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                  child: Row(
                    children: [
                      Container(
                        width: 100,
                        child: Text(
                          data[index]["key"] + ":",
                          style: TextStyle(
                              color: Colors.black54, fontSize: 14, height: 1.5),
                        ),
                      ),
                      Expanded(
                          child: Container(
                              margin: EdgeInsets.only(left: 10),
                              child: Text(
                                data[index]["val"].toString(),
                                style: TextStyle(
                                    color: Colors.black87,
                                    fontSize: 14,
                                    height: 1.5),
                              ))),
                      Container(
                        width: 50,
                        child: RaisedButton(
                            child: Text("删除"),
                            onPressed: () {
                              UserUtils.clearSettings();
                              getData();
                            }),
                      ),
                    ],
                  ));
            },
          ),
        ));
  }
}
