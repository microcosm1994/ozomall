import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:ozomall_flutter/api/goodsApi.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/pages/goodsDetail/brand.dart';
import 'package:ozomall_flutter/pages/goodsDetail/recentBuy.dart';
import 'package:ozomall_flutter/pages/goodsDetail/wear.dart';
import 'package:ozomall_flutter/utils/userUtils.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';
import 'package:ozomall_flutter/widget/goodsTitleCard/index.dart';
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
  List goodsSku = [];
  int spe1CurrentIndex = 0; // 属性索引
  int spe2CurrentIndex = 0;
  int spe3CurrentIndex = 0;
  int spe1CurrentId = 0; // 属性id
  int spe2CurrentId = 0;
  int spe3CurrentId = 0;
  String spe1CurrentName = ""; // 属性名称
  String spe2CurrentName = "";
  String spe3CurrentName = "";
  String skuInfoCover; // sku商品图片
  Map skuInfo; // sku商品规格
  String goodsPriceStr = ""; // sku弹出框商品规格

  // 获取商品详情
  void getGoodsDetail() {
    GoodsApi.getGoodsDetail({"id": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsInfo = res["data"];
        });
      }
    });
  }

  // 获取商品图片
  void getGoodsPic() {
    GoodsApi.getGoodsPic({"goodsId": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsPics = res["data"];
        });
      }
    });
  }

  // 获取商品参数
  void getGoodsParams() {
    GoodsApi.getGoodsParams({"goodsId": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsParams = res["data"];
        });
      }
    });
  }

  // 获取商品规格
  void getGoodsAttr() {
    GoodsApi.getGoodsAttr({"goodsId": widget.id}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsSku = res["data"];
        });
        print(goodsSku);
        for (var i = 0; i < goodsSku.length; i++) {
          int speId = goodsSku[i]["children"][0]["id"];
          String speName = goodsSku[i]["children"][0]["value"];
          switch (i) {
            case 0:
              spe1CurrentId = speId;
              spe1CurrentName = speName;
              break;
            case 1:
              spe2CurrentId = speId;
              spe2CurrentName = speName;
              break;
            case 2:
              spe3CurrentId = speId;
              spe3CurrentName = speName;
              break;
            default:
              break;
          }
        }
        // 获取初始价格
        getGoodsSkuPrice(spe1CurrentId, spe2CurrentId, spe3CurrentId, null);
      }
    });
  }

  // 获取商品价格
  Future getGoodsSkuPrice(int spe1Id, int spe2Id, int spe3Id, setState) {
    return GoodsApi.getGoodsSkuPrice(
        {"spe1Id": spe1Id, "spe2Id": spe2Id, "spe3Id": spe3Id}).then((res) {
      if (res["code"] == 1) {
        print(res);
        if (setState == null) {
          this.setState(() {
            skuInfo = res["data"][0];
            skuInfoCover = skuInfo["pic"];
            if (skuInfo["stock"] > 0) {
              goodsPriceStr = skuInfo["price"].toString();
            } else {
              goodsPriceStr = "暂时无货";
            }
          });
        } else {
          setState(() {
            skuInfo = res["data"][0];
            skuInfoCover = skuInfo["pic"];
            if (skuInfo["stock"] > 0) {
              goodsPriceStr = skuInfo["price"].toString();
            } else {
              goodsPriceStr = "暂时无货";
            }
          });
        }
      }
    });
  }

  // 点击购买
  void buy() async {
    var token = await UserUtils.getToken();
    if (token == null) {
      navigatorKey.currentState.pushNamed("/login");
    } else {
      // 提交订单
    }
  }

  @override
  void initState() {
    getGoodsDetail();
    getGoodsPic();
    getGoodsParams();
    getGoodsAttr();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color(0xf5f5f5f5),
        appBar: buildAppBar(),
        body: buildBody(context),
        bottomNavigationBar: buildNavigationBar(context));
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
  Widget buildBody(BuildContext context) {
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
        onTap: () => buildGoodsSku(context),
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
      goodsInfo == null
          ? Text("")
          : RecentBuy(
              goodsInfo: goodsInfo,
            ),
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
          ])),
    ]);
  }

  // bottomNavigationBar
  Widget buildNavigationBar(BuildContext context) {
    // TODO: implement build
    return Container(
        height: 60,
        padding: EdgeInsets.symmetric(vertical: 5),
        color: Colors.white,
        child: Row(children: [
          Container(
              height: 40,
              child: FlatButton(
                onPressed: () {},
                child: Text.rich(TextSpan(children: [
                  WidgetSpan(child: Icon(Icons.star_border)),
                  WidgetSpan(child: Text("收藏"))
                ])),
              )),
          Container(
              width: 100,
              height: 40,
              decoration: new BoxDecoration(
                  border: new Border.all(color: Colors.black45, width: 1),
                  borderRadius: new BorderRadius.circular((4.0))),
              child: FlatButton(
                color: Colors.white,
                onPressed: () {},
                child: Text("出售"),
              )),
          Expanded(
            child: Container(
                height: 40,
                padding: EdgeInsets.symmetric(horizontal: 20),
                child: RaisedButton(
                    color: Color(0xff56C0C1),
                    textColor: Colors.white,
                    child: Text("立即购买"),
                    onPressed: () => buildGoodsSku(context))),
          )
        ]));
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

  // 商品规格
  buildGoodsSku(BuildContext context) {
    if (skuInfo != null) {
      if (skuInfo["stock"] > 0) {
        goodsPriceStr = skuInfo["price"].toString();
      } else {
        goodsPriceStr = "暂时无货";
      }
    }
    return showModalBottomSheet(
        isScrollControlled: true,
        context: context,
        builder: (context) => Container(
            height: 400,
            child: StatefulBuilder(
                builder: (context, setState) => BottomSheet(
                      backgroundColor: Color(0xf5f5f5f5),
                      builder: (BuildContext context) {
                        return Column(
                          children: [
                            Container(
                                height: 90,
                                child: GoodsTitleCard(
                                  cover: skuInfo == null
                                      ? goodsInfo["cover"]
                                      : skuInfoCover,
                                  goodsName: goodsPriceStr,
                                  goodsPrice: spe1CurrentName +
                                      spe2CurrentName +
                                      spe3CurrentName,
                                )),
                            Expanded(
                                child: Container(
                                    child: ListView(
                                        padding: EdgeInsets.symmetric(
                                            horizontal: 10),
                                        children: buildSkuItem(setState)))),
                            Container(
                                color: Colors.white,
                                width: double.infinity,
                                height: 50,
                                padding: EdgeInsets.symmetric(
                                    horizontal: 10, vertical: 5),
                                child: RaisedButton(
                                    color: Color(0xff56C0C1),
                                    textColor: Colors.white,
                                    child: Text("立即购买"),
                                    onPressed: buy)),
                          ],
                        );
                      },
                      onClosing: () {},
                    ))));
  }

  // 渲染商品属性
  List<Widget> buildSkuItem(setState) {
    List<Widget> skuItem = [];
    for (var i = 0; i < goodsSku.length; i++) {
      Widget title = Container(
          width: double.infinity,
          padding: EdgeInsets.symmetric(vertical: 10),
          height: 40,
          child: Text(goodsSku[i]["name"], textAlign: TextAlign.left));
      skuItem.add(title);
      Widget content = GridView.builder(
          shrinkWrap: true,
          physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
          itemCount: goodsSku[i]["children"].length,
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              //横轴元素个数
              crossAxisCount: 4,
              //纵轴间距
              mainAxisSpacing: 20.0,
              //横轴间距
              crossAxisSpacing: 10.0,
              //子组件宽高长度比例
              childAspectRatio: 2.5),
          itemBuilder: (BuildContext context, int index) {
            var border; // sku元素边框，选中显示
            var BoxBorder = BoxDecoration(
                border: new Border.all(color: Colors.black87, width: 1),
                color: Colors.white,
                borderRadius: new BorderRadius.circular((4.0)));
            var speId = goodsSku[i]["children"][index]["id"]; // speId
            String speName = goodsSku[i]["children"][index]["value"];
            if (i == 0 && spe1CurrentIndex == index) {
              border = BoxBorder;
            }
            if (i == 1 && spe2CurrentIndex == index) {
              border = BoxBorder;
            }
            if (i == 2 && spe3CurrentIndex == index) {
              border = BoxBorder;
            }
            return Container(
                decoration: border,
                child: FlatButton(
                    color: Colors.white,
                    onPressed: () {
                      // 点击重新设置sku信息
                      setState(() {
                        if (i == 0) {
                          spe1CurrentIndex = index;
                          spe1CurrentId = speId;
                          spe1CurrentName = speName;
                        }
                        if (i == 1) {
                          spe2CurrentIndex = index;
                          spe2CurrentId = speId;
                          spe2CurrentName = speName;
                        }
                        if (i == 2) {
                          spe3CurrentIndex = index;
                          spe3CurrentId = speId;
                          spe3CurrentName = speName;
                        }
                      });
                      getGoodsSkuPrice(spe1CurrentId, spe2CurrentId,
                          spe3CurrentId, setState);
                    },
                    child: Text(
                      speName,
                      textAlign: TextAlign.center,
                      style: TextStyle(fontSize: 16, height: 1),
                    )));
          });
      skuItem.add(content);
    }
    return skuItem;
  }
}
