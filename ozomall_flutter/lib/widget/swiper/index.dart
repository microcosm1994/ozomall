import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class SwiperCustom extends StatefulWidget {
  SwiperCustom({Key key}) : super(key: key);
  @override
  _SwiperCustomState createState() => _SwiperCustomState();
}

class _SwiperCustomState extends State<SwiperCustom> {
  List swiperList = [
    "http://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/banner/1596705552540.jpg?Expires=1912065546&OSSAccessKeyId=LTAIXx905tkhWOmO&Signature=CzqDnzlIli%2BMEfD35zNIfywYc4I%3D",
    "http://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/banner/1596705591861.jpg?Expires=1912065585&OSSAccessKeyId=LTAIXx905tkhWOmO&Signature=X4NGBywGCrgIe6LCPq8aOnQj3Ds%3D",
    "http://ozomall-goods-pic.oss-cn-beijing.aliyuncs.com/banner/1596705606801.jpg?Expires=1912065602&OSSAccessKeyId=LTAIXx905tkhWOmO&Signature=Das%2BqA%2BIyRHqw96s9qAcf10Xev0%3D"
  ];
  void getData() {
    // // 取排行榜前三作为轮播图
    // List arr = List();
    // for (int i = 0; i < widget.books.length; i++) {
    //   if (i < 3) {
    //     arr.add(widget.books[i]['cover']);
    //   }
    // }
    // this.setState(() {
    //   this.swiperList = arr;
    // });
  }

  @override
  void initState() {
    // TODO: implement initState
    // this.getData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        height: 150.0,
        child: Swiper(
          itemBuilder: buildImage,
          itemCount: 3,
          pagination: new SwiperPagination(
              builder: DotSwiperPaginationBuilder(
            color: Colors.black54,
            activeColor: Colors.white,
          )),
          scrollDirection: Axis.horizontal,
          autoplay: true,
          onTap: (index) => print('点击了第$index个'),
        ));
  }

  // buildImage
  Widget buildImage(BuildContext context, int index) {
    return (Image.network(
      swiperList[index],
      scale: 8.5,
      fit: BoxFit.cover,
    ));
  }
}
