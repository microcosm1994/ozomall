import 'package:flutter/material.dart';

class Search extends StatefulWidget {
  Search({Key key}) : super(key: key);

  @override
  _SearchState createState() => _SearchState();
}

class _SearchState extends State<Search> {
  String searchValue = "";
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(70),
        child: buildAppBar(),
      ),
    );
  }

  // AppBar
  AppBar buildAppBar() {
    return AppBar(
      actions: [
        Padding(
            padding: EdgeInsets.only(right: 10.0),
            child: Container(
                width: 60,
                child: FlatButton(
                  child: Text(
                    "取消",
                    style: TextStyle(color: Colors.black87),
                  ),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                )))
      ],
      automaticallyImplyLeading: false,
      leading: null,
      backgroundColor: Colors.white,
      elevation: 0, // 消除底部阴影
      title: Container(
        width: double.infinity,
        height: 40,
        child: buildTextField(),
      ),
    );
  }

  // TextField
  TextField buildTextField() {
    final controller = TextEditingController(); // 输入框控制器
    controller.text = searchValue;
    return TextField(
      maxLines: 1, //最大行数
      autocorrect: false, //是否自动更正
      style: TextStyle(fontSize: 14.0, color: Colors.black87), //输入文本的样式
      decoration: InputDecoration(
          fillColor: Colors.grey[200],
          filled: true,
          disabledBorder: InputBorder.none,
          enabledBorder: InputBorder.none,
          focusedBorder: InputBorder.none,
          hintText: '搜索内容',
          // 输入框前面的小部件
          prefixIcon: Icon(
            Icons.search,
            color: Colors.black54,
          )),
      onSubmitted: (text) {
        //内容提交(按回车)的回调
        // this.getSearch(text);
        this.searchValue = text;
        debugPrint('submit $text');
      },
      controller: controller,
    );
  }
}
