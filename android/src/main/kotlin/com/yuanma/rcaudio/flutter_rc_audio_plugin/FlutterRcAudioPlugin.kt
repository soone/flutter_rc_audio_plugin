package com.yuanma.rcaudio.flutter_rc_audio_plugin

import androidx.annotation.NonNull
import cn.rongcloud.rtc.wrapper.constants.RCRTCIWAudioDeviceType
import cn.rongcloud.rtc.wrapper.flutter.RCRTCEngineWrapper
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWAudioRouteingListener

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterRcAudioPlugin */
class FlutterRcAudioPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private var audioRouteingListener: IRCRTCIWAudioRouteingListener? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_rc_audio_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            "startAudioRouteing" -> {
                startAudioRouteing()
                result.success("ok")
            }
            "stopAudioRouteing" -> {
                stopAudioRouteing()
                result.success("ok")
            }
            "resetAudioRouteing" -> {
                resetAudioRouteing()
                result.success("ok")
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun startAudioRouteing() {
        if (audioRouteingListener == null) audioRouteingListener = AudioRouteingListener()

        RCRTCEngineWrapper.getInstance().startAudioRouteing(audioRouteingListener)
    }

    private fun stopAudioRouteing() {
        RCRTCEngineWrapper.getInstance().stopAudioRouteing()
        if (audioRouteingListener != null) audioRouteingListener = null
    }

    private fun resetAudioRouteing() {
        RCRTCEngineWrapper.getInstance().resetAudioRouteingState()
    }
}
