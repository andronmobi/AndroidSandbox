package fr.dappli.androidsandbox.compose

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
import fr.dappli.androidsandbox.navigation.CompositionLocal
import fr.dappli.androidsandbox.navigation.NonStrongSkipping
import fr.dappli.androidsandbox.navigation.PerformanceMistake1
import fr.dappli.androidsandbox.navigation.PerformanceMistake2
import fr.dappli.androidsandbox.navigation.PerformanceMistake3
import fr.dappli.androidsandbox.navigation.PerformanceMistake4
import fr.dappli.androidsandbox.navigation.PerformanceMistake5
import fr.dappli.androidsandbox.navigation.PerformanceMistake6
import fr.dappli.androidsandbox.navigation.StrongSkipping
import fr.dappli.androidsandbox.ui.NavItem
import fr.dappli.androidsandbox.ui.NavItemCard
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme

val composeItems = listOf(
    NavItem(
        title = "CompositionLocal",
        subtitle = "compositionLocalOf vs staticCompositionLocalOf",
        key = CompositionLocal
    ),
    NavItem(
        title = "Non strong skipping mode",
        subtitle = "Stability and skipping when strong skipping is off",
        key = NonStrongSkipping
    ),
    NavItem(
        title = "Strong skipping mode",
        subtitle = "Same samples with strong skipping on (the default)",
        key = StrongSkipping
    ),
    NavItem(
        title = "Performance mistake 1",
        subtitle = "Filtering a list in composition without remember",
        key = PerformanceMistake1
    ),
    NavItem(
        title = "Performance mistake 2",
        subtitle = "ViewModel rebuilding lists on unrelated changes",
        key = PerformanceMistake2
    ),
    NavItem(
        title = "Performance mistake 3",
        subtitle = "Passing the whole state object instead of the fields",
        key = PerformanceMistake3
    ),
    NavItem(
        title = "Performance mistake 4",
        subtitle = "Reading fast-changing state too high",
        key = PerformanceMistake4
    ),
    NavItem(
        title = "Performance mistake 5",
        subtitle = "Lambdas capturing a value recomputed in composition",
        key = PerformanceMistake5
    ),
    NavItem(
        title = "Performance mistake 6",
        subtitle = "LazyColumn without keys",
        key = PerformanceMistake6
    ),
)

@Composable
fun ComposeScreen(onItemClick: (NavKey) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(composeItems, key = { it.title }) { item ->
            NavItemCard(item, onClick = { onItemClick(item.key) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeScreenPreview() {
    AndroidSandboxTheme {
        ComposeScreen(onItemClick = {})
    }
}
