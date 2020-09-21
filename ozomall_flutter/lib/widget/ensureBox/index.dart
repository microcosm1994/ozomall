import 'package:flutter/material.dart';

class EnsureBox extends StatelessWidget {
  const EnsureBox({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        height: 35.0,
        color: Colors.white,
        child: Row(
          children: [
            Expanded(
                child: Text.rich(
              TextSpan(
                  style: TextStyle(
                    fontSize: 12.0,
                    color: Colors.black54,
                  ),
                  children: [
                    WidgetSpan(
                        child: Icon(
                      Icons.verified_user,
                      size: 18.0,
                    )),
                    TextSpan(text: "正品保障")
                  ]),
              textAlign: TextAlign.center,
            )),
            Expanded(
                child: Text.rich(
              TextSpan(
                  style: TextStyle(
                      fontSize: 12.0,
                      color: Colors.black54,
                      textBaseline: TextBaseline.alphabetic),
                  children: [
                    WidgetSpan(
                        child: Icon(
                      Icons.verified_user,
                      size: 18.0,
                    )),
                    TextSpan(text: "逐件查验")
                  ]),
              textAlign: TextAlign.center,
            )),
            Expanded(
                child: Text.rich(
              TextSpan(
                  style: TextStyle(
                      fontSize: 12.0,
                      color: Colors.black54,
                      textBaseline: TextBaseline.alphabetic),
                  children: [
                    WidgetSpan(
                        child: Icon(
                      Icons.verified_user,
                      size: 18.0,
                    )),
                    TextSpan(text: "多重鉴别")
                  ]),
              textAlign: TextAlign.center,
            )),
          ],
        ));
  }
}
