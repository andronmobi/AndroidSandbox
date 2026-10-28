package fr.dappli.androidsandbox.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import fr.dappli.androidsandbox.navigation.Playback
import fr.dappli.androidsandbox.ui.NavItem
import fr.dappli.androidsandbox.ui.NavItemCard
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme
import fr.dappli.common.SectionTitle

private fun Stream.toNavItem() = NavItem(
    title = title,
    subtitle = "${format.label} · $description",
    key = Playback(id)
)

private val live = streams.filter { it.isLive }.map { it.toNavItem() }

private val onDemand = streams.filterNot { it.isLive }.map { it.toNavItem() }

@Composable
fun PlayerScreen(onItemClick: (NavKey) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SectionTitle("Live")
        }
        items(live, key = { it.key.toString() }) { item ->
            NavItemCard(item, onClick = { onItemClick(item.key) })
        }
        item {
            SectionTitle("On demand")
        }
        items(onDemand, key = { it.key.toString() }) { item ->
            NavItemCard(item, onClick = { onItemClick(item.key) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerScreenPreview() {
    AndroidSandboxTheme {
        PlayerScreen(onItemClick = {})
    }
}
