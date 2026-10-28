package fr.dappli.androidsandbox.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import fr.dappli.androidsandbox.player.PlaybackScreen
import fr.dappli.androidsandbox.player.PlayerScreen
import fr.dappli.androidsandbox.player.streamOf

fun EntryProviderScope<NavKey>.playerEntries(onNavigate: (NavKey) -> Unit) {
    entry<Player> {
        PlayerScreen(onItemClick = onNavigate)
    }
    entry<Playback> { key ->
        streamOf(key.streamId)?.let { PlaybackScreen(it) }
    }
}
