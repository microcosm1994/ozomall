import 'package:flutter/material.dart';

class GoodsTitleCard extends StatefulWidget {
  GoodsTitleCard(
      {Key key,
      @required this.cover,
      @required this.goodsName,
      @required this.goodsPrice})
      : super(key: key);
  final String cover;
  final String goodsName;
  final String goodsPrice;
  @override
  _GoodsTitleCardState createState() => _GoodsTitleCardState();
}

class _GoodsTitleCardState extends State<GoodsTitleCard> {
  @override
  Widget build(BuildContext context) {
    return Container(
        width: double.infinity,
        padding: EdgeInsets.symmetric(vertical: 10, horizontal: 10),
        child: Row(children: [
          Container(
            height: 50,
            width: 50,
            child: Image.network(
              widget.cover,
              scale: 1.0,
              fit: BoxFit.fitWidth,
            ),
          ),
          Expanded(
              child: Column(children: [
            Container(
              padding: EdgeInsets.symmetric(horizontal: 10),
              width: double.infinity,
              child: Text(
                widget.goodsName,
                style: TextStyle(
                    color: Colors.black87,
                    fontSize: 16,
                    fontWeight: FontWeight.w600),
                textAlign: TextAlign.left,
                maxLines: 2,
              ),
            ),
            Container(
              padding: EdgeInsets.symmetric(horizontal: 10),
              width: double.infinity,
              child: Text.rich(
                TextSpan(text: "￥ ", children: [
                  WidgetSpan(
                      child: Text(widget.goodsPrice,
                          style: TextStyle(
                              height: 1.0,
                              color: Colors.black87,
                              fontSize: 16,
                              fontWeight: FontWeight.w600))),
                  WidgetSpan(
                      child: Text(" 起",
                          style:
                              TextStyle(color: Colors.black87, fontSize: 10)))
                ]),
                textAlign: TextAlign.left,
                style: TextStyle(color: Colors.black87, fontSize: 12),
              ),
            )
          ]))
        ]));
  }
}
