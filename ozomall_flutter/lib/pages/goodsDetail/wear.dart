import 'package:flutter/material.dart';

class Wear extends StatefulWidget {
  Wear({Key key}) : super(key: key);

  @override
  _WearState createState() => _WearState();
}

class _WearState extends State<Wear> {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 2),
      color: Colors.white,
      width: double.infinity,
      child: Column(children: [
        Container(
            padding: EdgeInsets.symmetric(vertical: 4, horizontal: 10),
            height: 40,
            child: Row(children: [
              Expanded(
                  child: Text(
                "穿搭精选",
                style: TextStyle(color: Colors.black87, fontSize: 14),
              )),
              Container(
                  height: 20,
                  padding: EdgeInsets.symmetric(horizontal: 6),
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.black54, width: 1)),
                  child: Text(
                    "发布 +",
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                    textAlign: TextAlign.right,
                  )),
            ])),
        GridView.builder(
            shrinkWrap: true,
            physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3, childAspectRatio: 2.0),
            itemCount: 8, // 元素数量
            itemBuilder: (BuildContext context, int index) {
              return Container(
                  width: 50.0,
                  height: 50.0,
                  child: Image.asset(
                    "lib/assets/u=1691093550,1795227220&fm=26&gp=0.jpg",
                    fit: BoxFit.contain,
                  ));
            }),
        Container(
            padding: EdgeInsets.symmetric(vertical: 4, horizontal: 10),
            height: 40,
            child: Row(children: [
              Expanded(
                  child: Text(
                "查看全部",
                style: TextStyle(color: Colors.black54, fontSize: 14),
              )),
              Text.rich(TextSpan(children: [
                WidgetSpan(
                    child: Icon(
                  Icons.navigate_next,
                  color: Colors.black54,
                ))
              ])),
            ]))
      ]),
    );
  }
}
