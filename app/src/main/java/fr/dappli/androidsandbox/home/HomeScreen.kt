package fr.dappli.androidsandbox.home

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
import fr.dappli.androidsandbox.navigation.Compose
import fr.dappli.androidsandbox.ui.NavItem
import fr.dappli.androidsandbox.ui.NavItemCard
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme

val homeItems = listOf(
    NavItem(
        title = "Compose",
        subtitle = "CompositionLocal, skipping modes and performance mistakes",
        key = Compose
    ),
)

@Composable
fun HomeScreen(onItemClick: (NavKey) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(homeItems, key = { it.title }) { item ->
            NavItemCard(item, onClick = { onItemClick(item.key) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AndroidSandboxTheme {
        HomeScreen(onItemClick = {})
    }
}
