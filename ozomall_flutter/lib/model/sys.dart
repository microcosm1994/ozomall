import 'package:flutter/foundation.dart';

class SysProvider with ChangeNotifier {
  int _currentIndex = 0; // 底部选项卡当前选中index

  int get currentIndex => _currentIndex;

  void setCurrentIndex(int val) {
    _currentIndex = val;
    notifyListeners();
  }
}
