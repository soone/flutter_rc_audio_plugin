package com.yuanma.rcaudio.flutter_rc_audio_plugin

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import cn.rongcloud.rtc.api.RCRTCAudioRouteManager
import cn.rongcloud.rtc.wrapper.constants.RCRTCIWAudioDeviceErrorType
import cn.rongcloud.rtc.wrapper.constants.RCRTCIWVideoDeviceErrorType
import cn.rongcloud.rtc.wrapper.flutter.RCRTCEngineWrapper
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWAudioRouteingListener
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWLocalDeviceErrorListener
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.lang.ref.WeakReference

/** FlutterRcAudioPlugin */
class FlutterRcAudioPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private var audioRouteingListener: IRCRTCIWAudioRouteingListener? = null
    private var activity: FlutterActivity? = null

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
                setLocalDeviceErrorListener();
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
            "isHeadSetOn" -> {
                result.success(isHeadSetOn())
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun setLocalDeviceErrorListener(): Int {
        return  RCRTCEngineWrapper.getInstance()
            .setLocalDeviceErrorListener(object : IRCRTCIWLocalDeviceErrorListener {
                override fun onAudioDeviceError(type: RCRTCIWAudioDeviceErrorType?) {
                    activity?.runOnUiThread {
                        Log.e("=======setLocalDeviceErrorListener1", type?.toString())
                        channel.invokeMethod("localAudioDeviceError", type?.ordinal)
                    }
                }

                override fun onVideoDeviceError(type: RCRTCIWVideoDeviceErrorType?) {
                    activity?.runOnUiThread {
                        Log.e("=======setLocalDeviceErrorListener2", type?.toString())
                        channel.invokeMethod("localVideoDeviceError", type?.ordinal)
                    }
                }
            })
    }

    private fun startAudioRouteing() {
        if (audioRouteingListener == null) audioRouteingListener = AudioRouteingListener(fun(p0: Int) {
            Log.e("audioRouteStatus", p0.toString())
            channel.invokeMethod("audioRouteStatus", p0)
        })

        RCRTCEngineWrapper.getInstance().startAudioRouteing(audioRouteingListener)
    }

    private fun isHeadSetOn() : Int {
        if (RCRTCAudioRouteManager.getInstance().hasInit()) {
            if (RCRTCAudioRouteManager.getInstance().hasHeadSet() || RCRTCAudioRouteManager.getInstance().hasBluetoothA2dpConnected()) {
                return 1
            }

            return 0
        }

        return -1
    }

    private fun stopAudioRouteing() {
        RCRTCEngineWrapper.getInstance().stopAudioRouteing()
        if (audioRouteingListener != null) audioRouteingListener = null
    }

    private fun resetAudioRouteing() {
        RCRTCEngineWrapper.getInstance().resetAudioRouteingState()
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterActivity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterActivity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }
}
