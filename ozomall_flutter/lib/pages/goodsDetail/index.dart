import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:ozomall_flutter/api/goodsDetail.dart';
import 'package:ozomall_flutter/pages/goodsDetail/brand.dart';
import 'package:ozomall_flutter/pages/goodsDetail/wear.dart';
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
  List goodsParams = [];
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

  // 获取商品参数
  void getGoodsParams() {
    GoodsDetailApi.getGoodsParams({"goodsId": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsParams = res["data"];
        });
      }
    });
  }

  @override
  void initState() {
    print(goodsInfo);
    getGoodsDetail();
    getGoodsPic();
    getGoodsParams();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color(0xf5f5f5f5),
        appBar: buildAppBar(),
        body: buildBody());
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
      // 商品名称
      buildTitle(),
      // 商品价格
      buildPrice(),
      // 商品规格
      Cell(
        title: "规格",
        describe: "已选 m",
        isArrow: true,
      ),
      // 商品品牌
      Cell(
        title: goodsInfo == null ? "" : goodsInfo["brandName"],
        describe: "进入品牌",
        isArrow: true,
        onTap: () {
          Navigator.push(context, new MaterialPageRoute(builder: (_) {
            return new BrandPage(
              brandId: goodsInfo == null ? "" : goodsInfo["brandId"],
            );
          }));
        },
      ),
      // 商品相关推荐
      buildRecommend(),
      // 最近购买
      buildRecentBuy(),
      // 商品评价
      buildEvaluate(),
      // 穿搭精选
      Wear(),
      // 商品详情
      Container(
          width: double.infinity,
          child: Column(children: [
            // 商品参数
            buildGoodsParams(),
            // 商品介绍
            buildGoodsDetail()
          ]))
    ]);
  }

  // 商品标题
  Widget buildTitle() {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 5),
      width: double.infinity,
      color: Colors.white,
      child: Text(
        "商品标题",
        textAlign: TextAlign.center,
        style: TextStyle(
            color: Colors.black54, fontSize: 14.0, fontWeight: FontWeight.w500),
        maxLines: 2,
      ),
    );
  }

  // 商品价格
  Widget buildPrice() {
    return Container(
        width: double.infinity,
        padding: EdgeInsets.symmetric(vertical: 5),
        color: Colors.white,
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
        ));
  }

  // 相关推荐
  Widget buildRecommend() {
    List<Widget> recommendList = [];
    for (var i = 0; i < 3; i++) {
      recommendList.add(buildGoodsCard());
    }
    return Cell(
      title: "相关推荐",
      describe: "全部",
      isArrow: true,
      childHeight: 100,
      child: Swiper(
        itemBuilder: (BuildContext context, int index) {
          return Row(children: recommendList);
        },
        itemCount: 3,
        pagination: new SwiperPagination(
            builder: DotSwiperPaginationBuilder(
          color: Colors.black54,
          activeColor: Colors.white,
        )),
        scrollDirection: Axis.horizontal,
        autoplay: false,
        onTap: (index) => print('点击了第$index个'),
      ),
    );
  }

  // 相关推荐商品卡片
  Widget buildGoodsCard() {
    return Expanded(
        child: Container(
      padding: EdgeInsets.all(4),
      width: double.infinity,
      child: Column(children: [
        Container(
            height: 50,
            child: Image.network(
              "http://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/goods/pics/1598433380615.jpg?Expires=1913793379&OSSAccessKeyId=LTAIXx905tkhWOmO&Signature=QNzoa6q3yQ6vzyHxEwA1wG6ODmw%3D",
              scale: 1.0,
              fit: BoxFit.fitHeight,
            )),
        Expanded(
            child: Text(
          "asdas按时大苏打实打实大大是d按时大苏打",
          maxLines: 2,
          textAlign: TextAlign.center,
          style: TextStyle(color: Colors.black54, fontSize: 12),
        )),
        Expanded(
            child: Text(
          "￥" + "355",
          maxLines: 1,
          textAlign: TextAlign.center,
          style: TextStyle(color: Colors.black87, fontSize: 14),
        )),
      ]),
    ));
  }

  // 最近购买
  Widget buildRecentBuy() {
    List<Widget> recentBuyList = [];
    for (var i = 0; i < 4; i++) {
      Widget item = Container(
          width: double.infinity,
          height: 20,
          child: Row(children: [
            Expanded(
                child: Row(children: [
              Container(
                  width: 20,
                  height: 20,
                  child: Image.network(
                    "http://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/goods/pics/1598433380615.jpg?Expires=1913793379&OSSAccessKeyId=LTAIXx905tkhWOmO&Signature=QNzoa6q3yQ6vzyHxEwA1wG6ODmw%3D",
                    scale: 1.0,
                    fit: BoxFit.fitWidth,
                  )),
              Container(
                  padding: EdgeInsets.all(4),
                  height: 20,
                  child: Text(
                    "asdas",
                    style: TextStyle(color: Colors.black87, fontSize: 12),
                  ))
            ])),
            Expanded(
                child: Text("XL",
                    style: TextStyle(color: Colors.black87, fontSize: 12))),
            Expanded(
                child: Text("￥499",
                    style: TextStyle(color: Colors.black87, fontSize: 12))),
            Expanded(
                child: Text(
              "9月26日",
              style: TextStyle(color: Colors.black54, fontSize: 12),
            )),
          ]));
      recentBuyList.add(item);
    }
    return Cell(
      title: "最近购买",
      describe: "全部",
      isArrow: true,
      childHeight: 80,
      child: Column(children: recentBuyList),
    );
  }

  // 商品评价
  Widget buildEvaluate() {
    return Cell(
      title: "商品评价",
      describe: "20%的人觉得尺码合适",
      isArrow: true,
      childHeight: 30,
      child: Row(children: [
        Expanded(
            child: Container(
                margin: EdgeInsets.symmetric(horizontal: 4),
                padding: EdgeInsets.symmetric(horizontal: 10),
                height: 30,
                color: Color(0xf5f5f5f5),
                child: Row(children: [
                  Text(
                    "偏小",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  ),
                  Expanded(
                      child: Text(
                    "12",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )),
                ]))),
        Expanded(
            child: Container(
                margin: EdgeInsets.symmetric(horizontal: 4),
                padding: EdgeInsets.symmetric(horizontal: 10),
                height: 30,
                color: Color(0xf5f5f5f5),
                child: Row(children: [
                  Text(
                    "偏小",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  ),
                  Expanded(
                      child: Text(
                    "12",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )),
                ]))),
        Expanded(
            child: Container(
                margin: EdgeInsets.symmetric(horizontal: 4),
                padding: EdgeInsets.symmetric(horizontal: 10),
                height: 30,
                color: Color(0xf5f5f5f5),
                child: Row(children: [
                  Text(
                    "偏小",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  ),
                  Expanded(
                      child: Text(
                    "12",
                    textAlign: TextAlign.right,
                    style: TextStyle(color: Colors.black54, fontSize: 12),
                  )),
                ])))
      ]),
    );
  }

  // 商品参数
  Widget buildGoodsParams() {
    List<Widget> paramsList = [];
    for (var i = 0; i < goodsParams.length; i++) {
      Widget param = Container(
          height: 30,
          child: Row(children: [
            Expanded(
                child: Text(
              goodsParams[i]["name"],
              style: TextStyle(color: Colors.black54, fontSize: 12),
            )),
            Expanded(
                child: Text(
              goodsParams[i]["value"],
              style: TextStyle(color: Colors.black54, fontSize: 12),
              textAlign: TextAlign.end,
            ))
          ]));
      paramsList.add(param);
    }

    return Container(
        padding: EdgeInsets.symmetric(vertical: 20, horizontal: 10),
        color: Colors.white,
        margin: EdgeInsets.only(top: 2),
        child: Column(children: [
          Container(
              height: 30,
              width: double.infinity,
              child: Text(
                "参数",
                style: TextStyle(color: Colors.black87, fontSize: 16),
                textAlign: TextAlign.left,
              )),
          Column(
            children: paramsList,
          )
        ]));
  }

  // 商品介绍
  Widget buildGoodsDetail() {
    return Container(
        padding: EdgeInsets.symmetric(vertical: 20, horizontal: 10),
        color: Colors.white,
        child: Column(children: [
          Container(
              height: 30,
              width: double.infinity,
              child: Text(
                "商品介绍",
                style: TextStyle(color: Colors.black87, fontSize: 16),
                textAlign: TextAlign.left,
              )),
          Html(data: goodsInfo == null ? "" : goodsInfo["details"])
        ]));
  }
}
