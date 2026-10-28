package fr.dappli.androidsandbox.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import fr.dappli.androidsandbox.player.PlaybackScreen
import fr.dappli.androidsandbox.player.PlayerScreen
import fr.dappli.androidsandbox.player.streamOf

fun EntryProviderScope<NavKey>.playerEntries(onNavigate: (NavKey) -> Unit) {
    entry<Player> {
        PlayerScreen(onItemClick = onNavigate)
    }
    // No pop animation: the video SurfaceView ignores alpha, so a fade keeps it fully visible
    // and delays the player release until the transition ends.
    entry<Playback>(
        metadata = NavDisplay.popTransitionSpec {
            EnterTransition.None togetherWith ExitTransition.None
        } + NavDisplay.predictivePopTransitionSpec {
            EnterTransition.None togetherWith ExitTransition.None
        }
    ) { key ->
        streamOf(key.streamId)?.let { PlaybackScreen(it) }
    }
}
