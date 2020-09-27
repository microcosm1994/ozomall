import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/goodsDetail.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';
import 'package:ozomall_flutter/widget/swiper/index.dart';

class GoodsDetail extends StatefulWidget {
  GoodsDetail({Key key, this.id}) : super(key: key);
  final int id;
  @override
  _GoodsDetailState createState() => _GoodsDetailState();
}

class _GoodsDetailState extends State<GoodsDetail> {
  Map goodsInfo;
  List goodsPics = [];
  // 获取商品详情
  void getGoodsDetail() {
    GoodsDetailApi.getGoodsDetail({"id": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsInfo = res["data"];
        });
      }
    });
  }

  // 获取商品图片
  void getGoodsPic() {
    GoodsDetailApi.getGoodsPic({"goodsId": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsPics = res["data"];
        });
      }
    });
  }

  @override
  void initState() {
    getGoodsDetail();
    getGoodsPic();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(appBar: buildAppBar(), body: buildBody());
  }

  // AppBar
  Widget buildAppBar() {
    return AppBar(
      backgroundColor: Colors.white,
      elevation: 0.1,
      leading: FlatButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: Icon(
            Icons.arrow_back_ios,
            color: Colors.black54,
          )),
      title: Text(""),
      actions: [
        Container(
            width: 50,
            child: FlatButton(
                onPressed: null,
                child: Icon(
                  Icons.headset_mic,
                  color: Colors.black54,
                ))),
        Container(
            width: 50,
            child: FlatButton(
                onPressed: null,
                child: Icon(
                  Icons.launch,
                  color: Colors.black54,
                ))),
        Container(
          width: 70,
          child: FlatButton(
              onPressed: null,
              child: Text(
                "求购",
                style: TextStyle(
                    color: Colors.black54,
                    fontSize: 16,
                    fontWeight: FontWeight.w600),
              )),
        ),
      ],
    );
  }

  // body
  Widget buildBody() {
    return ListView(children: [
      new SwiperCustom(
        swiperList: goodsPics,
        height: 240.0,
      ),
      Container(
        padding: EdgeInsets.symmetric(vertical: 5),
        width: double.infinity,
        child: Text(
          "商品标题",
          textAlign: TextAlign.center,
          style: TextStyle(
              color: Colors.black54,
              fontSize: 14.0,
              fontWeight: FontWeight.w500),
          maxLines: 2,
        ),
      ),
      Container(
          width: double.infinity,
          padding: EdgeInsets.symmetric(vertical: 5),
          child: Text.rich(
            TextSpan(text: "￥", children: [
              WidgetSpan(
                  child: Text("1099",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          color: Colors.black87,
                          fontSize: 18.0,
                          fontWeight: FontWeight.w600)))
            ]),
            textAlign: TextAlign.center,
            style: TextStyle(
                color: Colors.black87,
                fontSize: 12.0,
                fontWeight: FontWeight.w600),
          )),
      Cell(
        title: "规格",
        describe: "已选 m",
        isArrow: true,
      ),
    ]);
  }
}
