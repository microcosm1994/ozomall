import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class SwiperCustom extends StatefulWidget {
  final List swiperList;
  final double height;
  SwiperCustom({Key key, this.swiperList, this.height}) : super(key: key);
  @override
  _SwiperCustomState createState() => _SwiperCustomState();
}

class _SwiperCustomState extends State<SwiperCustom> {
  @override
  Widget build(BuildContext context) {
    return Container(
        height: widget.height,
        width: double.infinity,
        child: Swiper(
          itemBuilder: widget.swiperList == null ? null : buildImage,
          itemCount: widget.swiperList == null ? 0 : widget.swiperList.length,
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
    return Image.network(
      widget.swiperList[index]["url"],
      fit: BoxFit.cover,
    );
  }
}
