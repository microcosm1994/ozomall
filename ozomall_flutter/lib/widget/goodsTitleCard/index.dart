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
        color: Colors.white,
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
              height: 50,
              child: Text(
                widget.goodsName,
                style: TextStyle(
                    color: Colors.black87,
                    fontSize: 16,
                    height: 1.5,
                    fontWeight: FontWeight.w600),
                textAlign: TextAlign.left,
                maxLines: 2,
              ),
            ),
            Container(
              padding: EdgeInsets.symmetric(horizontal: 10),
              width: double.infinity,
              child: Text(
                widget.goodsPrice,
                textAlign: TextAlign.left,
                style: TextStyle(color: Colors.black87, fontSize: 12),
              ),
            )
          ]))
        ]));
  }
}
