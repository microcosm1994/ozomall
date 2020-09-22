import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class SwiperCustom extends StatefulWidget {
  final List swiperList;
  SwiperCustom({Key key, this.swiperList}) : super(key: key);
  @override
  _SwiperCustomState createState() => _SwiperCustomState();
}

class _SwiperCustomState extends State<SwiperCustom> {
  @override
  Widget build(BuildContext context) {
    return Container(
        height: 150.0,
        child: Swiper(
          itemBuilder: buildImage,
          itemCount: widget.swiperList.length,
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
