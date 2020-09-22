import 'package:flutter/material.dart';

class Cell extends StatefulWidget {
  final String title; // 标题
  final String describe; // 描述
  final String route; // 页面路由
  final Icon beforeIcon; // 开始前的组件
  final bool isArrow; // 是否显示小箭头
  final Widget child; // cell下方内容区
  const Cell(
      {Key key,
      this.beforeIcon,
      this.isArrow = false,
      this.title,
      this.describe = "",
      this.route,
      this.child})
      : super(key: key);

  get buildBeforeIcon => null; // cell下方的盒子

  @override
  _CellState createState() => _CellState();
}

class _CellState extends State<Cell> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    Widget arrow = Text("");
    if (widget.isArrow) {
      arrow = Icon(Icons.navigate_next, color: Colors.black38);
    }
    return Column(children: [
      Container(
          padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
          height: 50.0,
          color: Colors.white,
          margin: EdgeInsets.only(top: 4),
          child: Row(children: [
            Expanded(
                child: Text.rich(TextSpan(children: [
              WidgetSpan(
                  child: widget.beforeIcon == null
                      ? Text("")
                      : widget.beforeIcon), // 开始前的组件
              WidgetSpan(child: Text(widget.title)) // 标题
            ], style: TextStyle()))),
            Expanded(
                child: Text.rich(
              TextSpan(children: [
                WidgetSpan(
                    child: Text(
                  widget.describe,
                  style: TextStyle(color: Colors.black38, fontSize: 12),
                  
                )), // 描述
                WidgetSpan(child: arrow) // 结束后的组件
              ]),
              textAlign: TextAlign.end,
            ))
          ])),
      Container(
          padding: EdgeInsets.symmetric(horizontal: 20),
          height: widget.child == null ? 0 : 50,
          width: double.infinity,
          child: widget.child,
          color: Colors.white),
    ]);
  }
}
