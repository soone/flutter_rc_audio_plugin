package com.yuanma.rcaudio.flutter_rc_audio_plugin

import android.util.Log
import cn.rongcloud.rtc.wrapper.constants.RCRTCIWAudioDeviceType
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWAudioRouteingListener

class AudioRouteingListener(var handlerFunc: ((Int) -> Unit)?) : IRCRTCIWAudioRouteingListener {
    override fun onAudioDeviceRouted(p0: RCRTCIWAudioDeviceType?) {
        if (handlerFunc != null) {
            when(p0) {
                RCRTCIWAudioDeviceType.PHONE -> {
                    // 听筒
                    handlerFunc!!(0)
                }
                RCRTCIWAudioDeviceType.SPEAKER -> {
                    // 扬声器
                    handlerFunc!!(1)
                }
                RCRTCIWAudioDeviceType.BLUETOOTH_HEADSET -> {
                    // 蓝牙耳机
                    handlerFunc!!(2)
                }
                RCRTCIWAudioDeviceType.WIRED_HEADSET -> {
                    // 有线耳机
                    handlerFunc!!(3)
                }
                else ->  handlerFunc!!(1)
            }
        }
    }

    override fun onAudioDeviceRouteFailed(
        rcrtciwAudioDeviceType: RCRTCIWAudioDeviceType?,
        rcrtciwAudioDeviceType1: RCRTCIWAudioDeviceType?
    ) {
        Log.i("AudioRouteingListener", "onAudioDeviceRouteFailed")
    }
}