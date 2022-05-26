package com.yuanma.rcaudio.flutter_rc_audio_plugin

import cn.rongcloud.rtc.wrapper.constants.RCRTCIWAudioDeviceType
import cn.rongcloud.rtc.wrapper.listener.IRCRTCIWAudioRouteingListener

class AudioRouteingListener : IRCRTCIWAudioRouteingListener {
    override fun onAudioDeviceRouted(p0: RCRTCIWAudioDeviceType?) {
        TODO("Not yet implemented")
    }

    override fun onAudioDeviceRouteFailed(
        p0: RCRTCIWAudioDeviceType?,
        p1: RCRTCIWAudioDeviceType?
    ) {
        TODO("Not yet implemented")
    }
}