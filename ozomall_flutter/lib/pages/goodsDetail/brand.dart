import 'package:flutter/material.dart';
import 'package:ozomall_flutter/api/goods.dart';
import 'package:ozomall_flutter/widget/staggeredListView/index.dart';

class BrandPage extends StatefulWidget {
  BrandPage({Key key, this.brandId}) : super(key: key);
  final int brandId;
  @override
  _BrandPageState createState() => _BrandPageState();
}

class _BrandPageState extends State<BrandPage> {
  ScrollController controller = new ScrollController(); // 滚动控制器
  int toolBarIndex = 0; // 排序工具栏
  int iconIndex = 0; // 价格排序图标箭头高亮
  int goodsCount = 0; // 商品数量
  List goodsList = []; // 商品列表
  Map brandInfo; // 品牌信息
  String brandName = ""; // 品牌名称

  // 获取商品品牌信息
  void getGoodsBrandInfo() {
    GoodsApi.getGoodsBrandInfo({"id": widget.brandId}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          brandInfo = res["data"];
        });
      }
    });
  }

  // 获取商品数量
  void getGoodsCount() {
    GoodsApi.getGoodsCount({"brandId": widget.brandId}).then((res) {
      if (res["code"] == 1) {
        this.setState(() {
          goodsCount = res["data"];
        });
      }
    });
  }

  // 获取商品列表
  void getGoodsList() {
    GoodsApi.getGoodsList({"brandId": widget.brandId, "page": 1, "size": 10})
        .then((res) {
      if (res["code"] == 1) {
        print(res);
        this.setState(() {
          goodsList = res["data"]["records"];
        });
      }
    });
  }

  @override
  void initState() {
    getGoodsBrandInfo();
    getGoodsCount();
    getGoodsList();
    // TODO: implement initState
    super.initState();
    // 监听滚动位置
    controller.addListener(() {
      if (controller.offset > 50) {
        this.setState(() {
          brandName = brandInfo == null ? "" : brandInfo["name"];
        });
      } else {
        this.setState(() {
          brandName = "";
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xf5f5f5f5),
      body: CustomScrollView(controller: controller, slivers: [
        // AppBar
        buildAppBar(),
        // 品牌信息
        SliverToBoxAdapter(child: buildBrandInfo()),
        // 排序工具栏
        SliverPersistentHeader(
          delegate: SortToolBar(child: buildSortTooBar()),
          pinned: true,
        ),
        // 商品列表
        SliverToBoxAdapter(child: StaggeredListView(list: goodsList))
      ]),
    );
  }

  // Appbar
  Widget buildAppBar() {
    return SliverAppBar(
      backgroundColor: Colors.white,
      pinned: true,
      expandedHeight: 50.0,
      elevation: 0.1,
      leading: FlatButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: Icon(
            Icons.arrow_back_ios,
            color: Colors.black54,
          )),
      actions: [
        FlatButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Icon(
              Icons.launch,
              color: Colors.black54,
            ))
      ],
      flexibleSpace: FlexibleSpaceBar(
        centerTitle: true,
        title: Text(
          brandName,
          textAlign: TextAlign.center,
          style: TextStyle(color: Colors.black87),
        ),
      ),
    );
  }

  // 品牌信息
  Widget buildBrandInfo() {
    return Container(
        padding: EdgeInsets.symmetric(horizontal: 10),
        color: Colors.white,
        child: Column(
          children: [
            Container(
              height: 50,
              child: Row(children: [
                Container(
                    width: 40,
                    height: 40,
                    child: brandInfo == null
                        ? Text("")
                        : Image.network(brandInfo["url"],
                            scale: 1.0, fit: BoxFit.cover)),
                Expanded(
                    child: Container(
                        width: double.infinity,
                        padding:
                            EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                        child: Column(children: [
                          Container(
                              height: 20,
                              width: double.infinity,
                              child: Text(
                                brandInfo == null ? "" : brandInfo["name"],
                                maxLines: 1,
                                style: TextStyle(
                                    color: Colors.black87,
                                    fontSize: 16,
                                    fontWeight: FontWeight.w600),
                                textAlign: TextAlign.left,
                              )),
                          Container(
                              height: 20,
                              width: double.infinity,
                              child: Text(
                                "0人关注 | " + goodsCount.toString() + "款商品",
                                maxLines: 1,
                                style: TextStyle(
                                    color: Colors.black54, fontSize: 12),
                                textAlign: TextAlign.left,
                              )),
                        ]))),
                Container(
                    color: Colors.black87,
                    width: 70,
                    height: 30,
                    child: FlatButton(
                        onPressed: null,
                        child: Text(
                          "+关注",
                          style: TextStyle(color: Colors.white, fontSize: 12),
                        )))
              ]),
            ),
            Container(
                width: double.infinity,
                padding: EdgeInsets.only(bottom: 10),
                child: Text(
                  brandInfo == null ? "" : brandInfo["des"],
                  style: TextStyle(
                      color: Colors.black54, fontSize: 12, height: 1.5),
                  textAlign: TextAlign.start,
                ))
          ],
        ));
  }

  // 排序工具栏
  Widget buildSortTooBar() {
    return Container(
        color: Colors.white,
        height: 30,
        width: double.infinity,
        child: Row(
          children: [
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 0;
                      });
                    },
                    child: Text("综合",
                        style: TextStyle(
                            color: toolBarIndex == 0
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 1;
                      });
                    },
                    child: Text("销量",
                        style: TextStyle(
                            color: toolBarIndex == 1
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            FlatButton(
                onPressed: () {
                  this.setState(() {
                    toolBarIndex = 2;
                    print(toolBarIndex);
                    if (toolBarIndex == 2) {
                      iconIndex = iconIndex == 0 ? 1 : 0;
                    }
                  });
                },
                child: Text.rich(
                  TextSpan(text: "价格", children: [
                    WidgetSpan(
                        child: Container(
                            height: 20,
                            child: Column(
                              children: [
                                Container(
                                  height: 10,
                                  child: Icon(Icons.expand_less,
                                      size: 16,
                                      color: toolBarIndex == 2 && iconIndex == 0
                                          ? Color(0xff56C0C1)
                                          : Colors.black54),
                                ),
                                Container(
                                  height: 10,
                                  child: Icon(Icons.expand_more,
                                      size: 16,
                                      color: toolBarIndex == 2 && iconIndex == 1
                                          ? Color(0xff56C0C1)
                                          : Colors.black54),
                                )
                              ],
                            )))
                  ]),
                  style: TextStyle(
                      color: toolBarIndex == 2
                          ? Color(0xff56C0C1)
                          : Colors.black54),
                )),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 3;
                      });
                    },
                    child: Text("新品",
                        style: TextStyle(
                            color: toolBarIndex == 3
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
            Expanded(
                child: FlatButton(
                    onPressed: () {
                      this.setState(() {
                        toolBarIndex = 4;
                      });
                    },
                    child: Text("筛选",
                        style: TextStyle(
                            color: toolBarIndex == 4
                                ? Color(0xff56C0C1)
                                : Colors.black54)))),
          ],
        ));
  }
}

class SortToolBar extends SliverPersistentHeaderDelegate {
  final Widget child;
  final double height;
  SortToolBar({@required this.child, this.height = 30.0});

  @override
  Widget build(
      BuildContext context, double shrinkOffset, bool overlapsContent) {
    // TODO: implement build
    return this.child;
  }

  @override
  // TODO: implement maxExtent
  double get maxExtent => this.height;

  @override
  // TODO: implement minExtent
  double get minExtent => this.height;

  @override
  bool shouldRebuild(SliverPersistentHeaderDelegate oldDelegate) {
    // TODO: implement shouldRebuild
    return true;
  }
}
