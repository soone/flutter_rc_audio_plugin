import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class FlutterRcAudioPlugin {
  static const MethodChannel _channel = MethodChannel('flutter_rc_audio_plugin');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void setHandler(Future<dynamic> Function(MethodCall) handler) {
    _channel.setMethodCallHandler(handler);
  }

  static Future<String> startAudioRouteing() async {
    if (Platform.isIOS) return Future.value("ok");
    return await _channel.invokeMethod('startAudioRouteing');
  }

  static Future<String> stopAudioRouteing() async {
    if (Platform.isIOS) return Future.value("ok");
    return await _channel.invokeMethod('stopAudioRouteing');
  }

  static Future<String> resetAudioRouteing() async {
    if (Platform.isIOS) return Future.value("ok");
    return await _channel.invokeMethod("resetAudioRouteing");
  }

  static Future<int> isHeadSetOn() async {
    if (Platform.isIOS) return Future.value(0);
    return await _channel.invokeMethod("isHeadSetOn");
  }

  static Future<int> setImKey(String key) async {
    if (Platform.isIOS) return Future.value(0);
    Map<String, dynamic> arguments = {'key': key};
    return await _channel.invokeMethod("setImKey", arguments);
  }
}
