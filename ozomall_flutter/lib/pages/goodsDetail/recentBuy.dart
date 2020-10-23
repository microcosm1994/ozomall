import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/orderApi.dart';
import 'package:ozomall_flutter/widget/cell/index.dart';
import 'package:ozomall_flutter/widget/goodsTitleCard/index.dart';

class RecentBuy extends StatefulWidget {
  RecentBuy({Key key, @required this.goodsInfo}) : super(key: key);
  final Map goodsInfo;
  @override
  _RecentBuyState createState() => _RecentBuyState();
}

class _RecentBuyState extends State<RecentBuy> {
  List buyList;

  // 获取最近购买列表
  void getBuyList() {
    OrdersApi.getBuyList(
        {"goodsId": widget.goodsInfo["id"], "page": 1, "size": 20}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          buyList = res["data"]["records"];
        });
      }
    });
  }

  @override
  void initState() {
    // 获取最近购买列表
    getBuyList();
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Cell(
        title: "最近购买",
        describe: "全部",
        isArrow: true,
        childHeight: 100,
        child: buildList(),
        onTap: () {
          Navigator.push(context, new MaterialPageRoute(builder: (_) {
            return buildBuyPage();
          }));
        });
  }

  // 详情页最近购买
  Widget buildList() {
    return ListView.builder(
        physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
        itemCount: buyList == null ? 0 : 4,
        itemBuilder: (BuildContext context, int index) {
          return Container(height: 25, child: buildListCard(buyList[index]));
        });
  }

  // 最近购买页
  Widget buildBuyPage() {
    return Scaffold(
      backgroundColor: Color(0xf5f5f5f5),
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: FlatButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Icon(
              Icons.arrow_back_ios,
              color: Colors.black54,
            )),
        title: Text(
          "最近购买",
          style: TextStyle(
              color: Colors.black87, fontSize: 16, fontWeight: FontWeight.w600),
        ),
        centerTitle: true,
        elevation: 1,
        bottom: PreferredSize(
            child: GoodsTitleCard(
                cover: widget.goodsInfo["cover"],
                goodsName: widget.goodsInfo["goodsName"],
                goodsPrice: "￥" + widget.goodsInfo["goodsPrice"] + "起"),
            preferredSize: Size.fromHeight(70)),
      ),
      body: Container(
          color: Colors.white,
          width: double.infinity,
          padding: EdgeInsets.symmetric(horizontal: 10),
          child: ListView.builder(
              itemCount: buyList.length,
              itemBuilder: (BuildContext context, int index) {
                return Container(
                    height: 35, child: buildListCard(buyList[index]));
              })),
    );
  }

  // 最近购买列表卡片
  Widget buildListCard(Map data) {
    return Row(children: [
      Expanded(
          child: Row(children: [
        Container(
            width: 20,
            height: 20,
            child: Image.network(
              data["goodsPic"],
              scale: 1.0,
              fit: BoxFit.fitWidth,
            )),
        Container(
            padding: EdgeInsets.symmetric(vertical: 2, horizontal: 10),
            height: 20,
            child: Text(
              data["userInfo"]["nickName"],
              style: TextStyle(color: Colors.black87, fontSize: 10),
              textAlign: TextAlign.center,
            ))
      ])),
      Expanded(
          child: Text(
        data["spe1Name"] + " " + data["spe2Name"] + " " + data["spe3Name"],
        style: TextStyle(color: Colors.black87, fontSize: 10),
        textAlign: TextAlign.center,
      )),
      Expanded(
          child: Text(
        "￥" + data["goodsAmount"].toString(),
        style: TextStyle(color: Colors.black87, fontSize: 10),
        textAlign: TextAlign.center,
      )),
      Expanded(
          child: Text(
        "9月26日",
        style: TextStyle(color: Colors.black54, fontSize: 10),
        textAlign: TextAlign.right,
      )),
    ]);
  }
}
