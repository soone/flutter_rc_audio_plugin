package com.yuanma.rcaudio.flutter_rc_audio_plugin

import android.util.Log
import cn.rongcloud.rtc.wrapper.constants.RCRTCIWAudioDeviceType
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWAudioRouteingListener

class AudioRouteingListener : IRCRTCIWAudioRouteingListener {
    override fun onAudioDeviceRouted(p0: RCRTCIWAudioDeviceType?) {
        Log.i("AudioRouteingListener", "onAudioDeviceRouted")
    }

    override fun onAudioDeviceRouteFailed(
        rcrtciwAudioDeviceType: RCRTCIWAudioDeviceType?,
        rcrtciwAudioDeviceType1: RCRTCIWAudioDeviceType?
    ) {
        Log.i("AudioRouteingListener", "onAudioDeviceRouteFailed")
    }
}