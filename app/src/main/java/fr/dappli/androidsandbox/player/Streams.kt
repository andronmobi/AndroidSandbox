package fr.dappli.androidsandbox.player

import androidx.media3.common.MimeTypes

enum class StreamFormat(val label: String, val mimeType: String) {
    Hls("HLS", MimeTypes.APPLICATION_M3U8),
    Dash("DASH", MimeTypes.APPLICATION_MPD),
}

data class Stream(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val format: StreamFormat,
    val isLive: Boolean,
)

// Public test streams: stable demo endpoints rather than real channels, whose URLs
// change often or are protected.
val streams = listOf(
    Stream(
        id = "unified-live-hls",
        title = "Unified Streaming live",
        description = "Live HLS with a DVR window",
        url = "https://demo.unified-streaming.com/k8s/live/stable/live.isml/.m3u8",
        format = StreamFormat.Hls,
        isLive = true,
    ),
    Stream(
        id = "unified-live-dash",
        title = "Unified Streaming live",
        description = "Same live stream in DASH",
        url = "https://demo.unified-streaming.com/k8s/live/stable/live.isml/.mpd",
        format = StreamFormat.Dash,
        isLive = true,
    ),
    Stream(
        id = "unified-live-scte35",
        title = "Unified Streaming SCTE-35",
        description = "Live HLS with SCTE-35 ad markers",
        url = "https://demo.unified-streaming.com/k8s/live/stable/scte35.isml/.m3u8",
        format = StreamFormat.Hls,
        isLive = true,
    ),
    Stream(
        id = "dashif-livesim",
        title = "DASH-IF livesim",
        description = "Simulated live DASH with 2s segments",
        url = "https://livesim2.dashif.org/livesim2/testpic_2s/Manifest.mpd",
        format = StreamFormat.Dash,
        isLive = true,
    ),
    Stream(
        id = "apple-bipbop",
        title = "Apple bipbop",
        description = "fMP4 HLS with several qualities, audio tracks and subtitles",
        url = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8",
        format = StreamFormat.Hls,
        isLive = false,
    ),
    Stream(
        id = "mux-big-buck-bunny",
        title = "Big Buck Bunny (Mux)",
        description = "HLS test stream with several qualities",
        url = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
        format = StreamFormat.Hls,
        isLive = false,
    ),
    Stream(
        id = "unified-tears-of-steel",
        title = "Tears of Steel (Unified Streaming)",
        description = "DASH with several qualities and subtitles",
        url = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.mpd",
        format = StreamFormat.Dash,
        isLive = false,
    ),
)

fun streamOf(id: String): Stream? = streams.firstOrNull { it.id == id }
