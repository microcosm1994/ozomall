import 'package:flutter/material.dart';

class OrderConfirm extends StatefulWidget {
  OrderConfirm({Key key}) : super(key: key);

  @override
  _OrderConfirmState createState() => _OrderConfirmState();
}

class _OrderConfirmState extends State<OrderConfirm> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("确认订单"),
        leading: FlatButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Icon(
              Icons.arrow_back_ios,
              color: Colors.black54,
            )),
      ),
      body: Text("data"),
    );
  }
}
