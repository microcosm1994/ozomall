import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/addressApi.dart';
import 'package:ozomall_flutter/main.dart';
import 'package:ozomall_flutter/utils/userUtils.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';
import 'package:ozomall_flutter/widget/goodsTitleCard/index.dart';

class OrderConfirm extends StatefulWidget {
  OrderConfirm(
      {Key key,
      @required this.goodsId,
      @required this.goodsName,
      @required this.goodsSkuId,
      @required this.spe1Name,
      @required this.spe2Name,
      @required this.spe3Name,
      @required this.price,
      @required this.pic})
      : super(key: key);
  final int goodsId; // 商品id
  final String goodsName; // 商品名称
  final int goodsSkuId; // skuId
  final String spe1Name; // 商品属性1
  final String spe2Name; // 商品属性2
  final String spe3Name; // 商品属性3
  final int price; // 商品价格
  final String pic; // 商品图片
  @override
  _OrderConfirmState createState() => _OrderConfirmState();
}

class _OrderConfirmState extends State<OrderConfirm> {
  var addressInfo = null; // 地址信息
  // 获取地址
  void getAddress() async {
    var users = await UserUtils.getUserInfo();
    var userSettings = await UserUtils.getUserSettings();
    print(userSettings);
    AddressApi.getAddress({"userId": users["id"]}).then((res) {
      if (res["code"] == 1) {
        var addressList = res["data"];
        for (var i = 0; i < addressList.length; i++) {
          if (addressList[i]["id"] == userSettings["addressId"]) {
            this.setState(() {
              addressInfo = addressList[i];
            });
          }
        }
        if (addressInfo == null) {
          addressInfo = addressList[0];
        }
      }
    });
  }

  @override
  void initState() {
    getAddress();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xf5f5f5f5),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          "确认订单",
          style: TextStyle(color: Colors.black87, fontSize: 18),
        ),
        centerTitle: true,
        leading: FlatButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Icon(
              Icons.arrow_back_ios,
              color: Colors.black54,
            )),
      ),
      body: ListView(
        children: [buildAddress(), buildGoodsCard(), buildPrice()],
      ),
      bottomNavigationBar: buildBtn(),
    );
  }

  // 地址
  Widget buildAddress() {
    return Cell(
      beforeIcon: addressInfo == null
          ? null
          : Icon(
              Icons.location_on,
              color: Colors.black45,
              size: 16,
            ),
      title: addressInfo == null ? "添加地址" : addressInfo["consignee"],
      describe: addressInfo == null ? "" : addressInfo["phone"],
      isArrow: true,
      childHeight: addressInfo == null ? 0 : 30,
      child: addressInfo == null
          ? null
          : Container(
              width: double.infinity,
              height: 30,
              child: Text(
                "${addressInfo['region']}${addressInfo['address']}",
                style:
                    TextStyle(color: Colors.black54, fontSize: 12, height: 1.3),
              )),
      onTap: () {
        navigatorKey.currentState.pushNamed("/address");
      },
    );
  }

  // 商品信息
  Widget buildGoodsCard() {
    return Container(
        margin: EdgeInsets.only(top: 4),
        width: double.infinity,
        child: GoodsTitleCard(
          cover: widget.pic,
          title: Text(
            widget.goodsName,
            style: TextStyle(color: Colors.black87, fontSize: 14, height: 1.5),
          ),
          subTitle: Text(
            "${widget.spe1Name} ${widget.spe2Name} ${widget.spe3Name}",
            style: TextStyle(color: Colors.black45, fontSize: 12),
          ),
          des: Text("￥" + widget.price.toString(),
              style: TextStyle(
                  color: Colors.black,
                  fontSize: 14,
                  height: 2,
                  fontWeight: FontWeight.w500)),
        ));
  }

  // 价格信息
  Widget buildPrice() {
    return Container(
        margin: EdgeInsets.only(top: 4),
        width: double.infinity,
        child: Column(
          children: [
            Cell(title: "京东配送", describe: "￥14"),
            Cell(title: "优惠券", describe: "0"),
          ],
        ));
  }

  // 提交订单按钮
  Widget buildBtn() {
    return Container(
        padding: EdgeInsets.symmetric(horizontal: 10),
        width: double.infinity,
        height: 60,
        color: Colors.white,
        child: Row(
          children: [
            Expanded(
                child: Text.rich(TextSpan(
                    text: "实付款:￥",
                    children: [
                      WidgetSpan(
                          child: Text((widget.price + 14).toString(),
                              style: TextStyle(
                                  color: Colors.red,
                                  fontSize: 16,
                                  fontWeight: FontWeight.w500)))
                    ],
                    style: TextStyle(
                      color: Colors.red,
                      fontSize: 12,
                    )))),
            Container(
                width: 100,
                child: RaisedButton(
                    color: Color(0xff56C0C1),
                    onPressed: () {},
                    child: Text("提交订单", style: TextStyle(color: Colors.white))))
          ],
        ));
  }
}
