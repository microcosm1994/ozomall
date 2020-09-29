import 'package:flutter/material.dart';

class Cell extends StatefulWidget {
  final String title; // 标题
  final String describe; // 描述
  final String route; // 页面路由
  final Icon beforeIcon; // 开始前的组件
  final bool isArrow; // 是否显示小箭头
  final Widget child; // cell下方内容区
  final double childHeight; // cell下方内容区高度
  final VoidCallback onTap; // cell下方内容区高度
  const Cell(
      {Key key,
      this.beforeIcon,
      this.isArrow = false,
      this.title,
      this.describe = "",
      this.route,
      this.child,
      this.onTap,
      this.childHeight})
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
      GestureDetector(
        onTap: widget.onTap,
        child: Container(
            padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
            height: 40.0,
            color: Colors.white,
            margin: EdgeInsets.only(top: 2),
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
                      child: Container(
                          height: 40,
                          padding: EdgeInsets.symmetric(vertical: 10),
                          child: Text(
                            widget.describe,
                            style:
                                TextStyle(color: Colors.black54, fontSize: 12),
                          ))),
                  WidgetSpan(
                      child: Container(height: 40, child: arrow)) // 结束后的组件
                ], style: TextStyle(color: Colors.black38, fontSize: 12)),
                textAlign: TextAlign.end,
              ))
            ])),
      ),
      Container(
          padding: EdgeInsets.symmetric(horizontal: 20, vertical: 4),
          height: widget.childHeight == null ? 0 : widget.childHeight + 10,
          width: double.infinity,
          child: widget.child,
          color: Colors.white),
    ]);
  }
}
