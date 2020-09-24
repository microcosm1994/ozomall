import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:ozomall_flutter/widget/goodsCard/index.dart';

class StaggeredListView extends StatefulWidget {
  StaggeredListView({Key key, this.list}) : super(key: key);
  List list;
  @override
  _StaggeredListViewState createState() => _StaggeredListViewState();
}

class _StaggeredListViewState extends State<StaggeredListView> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return StaggeredGridView.countBuilder(
      shrinkWrap: true, //解决无限高度问题
      physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
      crossAxisCount: 4,
      itemCount: widget.list == null? 0 : widget.list.length, // 元素数量
      padding: EdgeInsets.symmetric(vertical: 4.0),
      primary: false,
      itemBuilder: (BuildContext context, int index) =>
          new GoodsCard(goodsInfo: widget.list[index]),
      staggeredTileBuilder: (int index) =>
          new StaggeredTile.fit(2),
      mainAxisSpacing: 4.0,
      crossAxisSpacing: 4.0,
    );
  }
}
