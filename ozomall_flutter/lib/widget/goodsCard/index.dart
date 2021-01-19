import 'package:flutter/material.dart';
import 'package:ozomall_flutter/pages/goodsDetail/index.dart';

class GoodsCard extends StatefulWidget {
  GoodsCard({Key key, this.goodsInfo}) : super(key: key);
  final Map goodsInfo;
  @override
  _GoodsCardState createState() => _GoodsCardState();
}

class _GoodsCardState extends State<GoodsCard> {
  @override
  void initState() {
    print(widget.goodsInfo);
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Container(
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
        constraints: BoxConstraints(minWidth: double.infinity, minHeight: 50),
        color: Colors.white,
        child: Column(children: [
          // 商品图片
          Container(
              width: double.infinity,
              child: widget.goodsInfo == null
                  ? Text("")
                  : new Image.network(
                      widget.goodsInfo["cover"],
                      fit: BoxFit.fitWidth,
                    )),
          // 商品名称
          Container(
              padding: EdgeInsets.symmetric(vertical: 5),
              width: double.infinity,
              child: Text(
                widget.goodsInfo["goodsName"],
                maxLines: 2,
                textAlign: TextAlign.left,
                style: TextStyle(fontSize: 14, color: Colors.black54),
              )),
          // 商品价格
          Container(
              padding: EdgeInsets.symmetric(vertical: 5),
              child: Row(children: [
                Expanded(
                    child: Text.rich(TextSpan(
                        text: "￥",
                        children: [
                          WidgetSpan(
                              child: Text(
                            widget.goodsInfo["goodsPrice"],
                            style: TextStyle(
                                fontSize: 14,
                                color: Colors.black87,
                                fontWeight: FontWeight.w500),
                          ))
                        ],
                        style:
                            TextStyle(fontSize: 12, color: Colors.black87)))),
                Expanded(
                    child: Text(
                  widget.goodsInfo["sales"].toString() + "人付款",
                  style: TextStyle(fontSize: 12, color: Colors.black54),
                  textAlign: TextAlign.end,
                )),
              ]))
        ]),
      ),
      onTap: () {
        Navigator.push(context, new MaterialPageRoute(builder: (_) {
          return new GoodsDetail(id: widget.goodsInfo["id"]);
        }));
      },
    );
  }
}
