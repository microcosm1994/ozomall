import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class StaggeredListView extends StatefulWidget {
  StaggeredListView({Key key, this.index}) : super(key: key);
  int index;
  @override
  _StaggeredListViewState createState() => _StaggeredListViewState();
}

class _StaggeredListViewState extends State<StaggeredListView> {
  @override
  void initState() {
    print(widget.index);
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return StaggeredGridView.countBuilder(
      shrinkWrap: true, //解决无限高度问题
      physics: new NeverScrollableScrollPhysics(), //禁用滑动事件
      crossAxisCount: 4,
      itemCount: 8,
      padding: EdgeInsets.symmetric(vertical: 4.0),
      itemBuilder: (BuildContext context, int index) => new Container(
          color: Colors.white,
          child: new Center(
            child: new CircleAvatar(
              backgroundColor: Colors.white,
              child: new Text(index.toString()),
            ),
          )),
      staggeredTileBuilder: (int index) =>
          new StaggeredTile.count(2, index.isEven ? 2 : 1),
      mainAxisSpacing: 4.0,
      crossAxisSpacing: 4.0,
    );
  }
}
