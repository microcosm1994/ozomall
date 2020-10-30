import 'package:flutter/material.dart';

class GoodsTitleCard extends StatefulWidget {
  GoodsTitleCard(
      {Key key,
      @required this.cover,
      @required this.title,
      @required this.subTitle,
      this.des})
      : super(key: key);
  final String cover;
  final Widget title;
  final Widget subTitle;
  final Widget des;
  @override
  _GoodsTitleCardState createState() => _GoodsTitleCardState();
}

class _GoodsTitleCardState extends State<GoodsTitleCard> {
  @override
  Widget build(BuildContext context) {
    return Container(
        color: Colors.white,
        width: double.infinity,
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
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
              child: widget.title,
            ),
            Container(
              padding: EdgeInsets.symmetric(horizontal: 10),
              width: double.infinity,
              child: widget.subTitle,
            ),
            widget.des == null
                ? Text("")
                : Container(
                    padding: EdgeInsets.symmetric(horizontal: 10),
                    width: double.infinity,
                    child: widget.des,
                  ),
          ]))
        ]));
  }
}
