import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mapbox_plugin/mapbox_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('mapbox_plugin');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });
}
