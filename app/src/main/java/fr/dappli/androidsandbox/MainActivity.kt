package fr.dappli.androidsandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import fr.dappli.androidsandbox.compose.compositionlocal.CompositionLocalScreen
import fr.dappli.androidsandbox.compose.performance.mistake1.PerformanceMistake1Screen
import fr.dappli.androidsandbox.compose.performance.mistake2.PerformanceMistake2Screen
import fr.dappli.androidsandbox.compose.performance.mistake3.PerformanceMistake3Screen
import fr.dappli.androidsandbox.compose.performance.mistake4.PerformanceMistake4Screen
import fr.dappli.androidsandbox.compose.strongskipping.page.ArrayListParameterPage as StrongArrayListParameterPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ArrayListPropertyPage as StrongArrayListPropertyPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ListParameterPage as StrongListParameterPage
import fr.dappli.androidsandbox.compose.strongskipping.page.MemoizationPage as StrongMemoizationPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ReadmePage as StrongReadmePage
import fr.dappli.androidsandbox.compose.strongskipping.page.VarPropertyPage as StrongVarPropertyPage
import fr.dappli.androidsandbox.navigation.CompositionLocal
import fr.dappli.androidsandbox.navigation.Home
import fr.dappli.androidsandbox.navigation.NonStrongSkipping
import fr.dappli.androidsandbox.navigation.PerformanceMistake1
import fr.dappli.androidsandbox.navigation.PerformanceMistake2
import fr.dappli.androidsandbox.navigation.PerformanceMistake3
import fr.dappli.androidsandbox.navigation.PerformanceMistake4
import fr.dappli.androidsandbox.navigation.StrongSkipping
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme
import fr.dappli.common.SectionTitle
import fr.dappli.common.SkippingScreen
import fr.dappli.nostrongskipping.page.ArrayListParameterPage
import fr.dappli.nostrongskipping.page.ArrayListPropertyPage
import fr.dappli.nostrongskipping.page.ListParameterPage
import fr.dappli.nostrongskipping.page.MemoizationPage
import fr.dappli.nostrongskipping.page.ReadmePage
import fr.dappli.nostrongskipping.page.VarPropertyPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidSandboxTheme {
                val backStack = rememberNavBackStack(Home)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(
                            title = backStack.lastOrNull()?.let(::titleOf)
                                ?: stringResource(R.string.app_name),
                            canNavigateBack = backStack.size > 1,
                            onBack = { backStack.removeLastOrNull() }
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreen(onItemClick = { backStack.add(it) })
                            }
                            entry<CompositionLocal> {
                                CompositionLocalScreen()
                            }
                            entry<NonStrongSkipping> {
                                SkippingScreen(
                                    readmeDoc = "nostrongskipping/README.md",
                                    varPropertyDoc = "nostrongskipping/VarProperty.md",
                                    arrayListParameterDoc = "nostrongskipping/ArrayListParameter.md",
                                    listParameterDoc = "nostrongskipping/ListParameter.md",
                                    arrayListPropertyDoc = "nostrongskipping/ArrayListProperty.md",
                                    memoizationDoc = "nostrongskipping/Memoization.md",
                                    readme = { ReadmePage(it) },
                                    varProperty = { VarPropertyPage(it) },
                                    arrayListParameter = { ArrayListParameterPage(it) },
                                    listParameter = { ListParameterPage(it) },
                                    arrayListProperty = { ArrayListPropertyPage(it) },
                                    memoization = { MemoizationPage(it) },
                                )
                            }
                            entry<StrongSkipping> {
                                SkippingScreen(
                                    readmeDoc = "strongskipping/README.md",
                                    varPropertyDoc = "strongskipping/VarProperty.md",
                                    arrayListParameterDoc = "strongskipping/ArrayListParameter.md",
                                    listParameterDoc = "strongskipping/ListParameter.md",
                                    arrayListPropertyDoc = "strongskipping/ArrayListProperty.md",
                                    memoizationDoc = "strongskipping/Memoization.md",
                                    readme = { StrongReadmePage(it) },
                                    varProperty = { StrongVarPropertyPage(it) },
                                    arrayListParameter = { StrongArrayListParameterPage(it) },
                                    listParameter = { StrongListParameterPage(it) },
                                    arrayListProperty = { StrongArrayListPropertyPage(it) },
                                    memoization = { StrongMemoizationPage(it) },
                                )
                            }
                            entry<PerformanceMistake1> {
                                PerformanceMistake1Screen()
                            }
                            entry<PerformanceMistake2> {
                                PerformanceMistake2Screen()
                            }
                            entry<PerformanceMistake3> {
                                PerformanceMistake3Screen()
                            }
                            entry<PerformanceMistake4> {
                                PerformanceMistake4Screen()
                            }
                        }
                    )
                }
            }
        }
    }
}

private data class HomeItem(val title: String, val subtitle: String, val key: NavKey)

private val composeItems = listOf(
    HomeItem(
        title = "CompositionLocal",
        subtitle = "compositionLocalOf vs staticCompositionLocalOf",
        key = CompositionLocal
    ),
    HomeItem(
        title = "Non strong skipping mode",
        subtitle = "Stability and skipping when strong skipping is off",
        key = NonStrongSkipping
    ),
    HomeItem(
        title = "Strong skipping mode",
        subtitle = "Same samples with strong skipping on (the default)",
        key = StrongSkipping
    ),
    HomeItem(
        title = "Performance mistake 1",
        subtitle = "Filtering a list in composition without remember",
        key = PerformanceMistake1
    ),
    HomeItem(
        title = "Performance mistake 2",
        subtitle = "ViewModel rebuilding lists on unrelated changes",
        key = PerformanceMistake2
    ),
    HomeItem(
        title = "Performance mistake 3",
        subtitle = "Passing the whole state object instead of the fields",
        key = PerformanceMistake3
    ),
    HomeItem(
        title = "Performance mistake 4",
        subtitle = "Reading fast-changing state too high",
        key = PerformanceMistake4
    ),
)

private fun titleOf(key: NavKey): String? =
    composeItems.firstOrNull { it.key == key }?.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, canNavigateBack: Boolean, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun HomeScreen(onItemClick: (NavKey) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SectionTitle("Compose")
        }
        items(composeItems, key = { it.title }) { item ->
            HomeItemCard(item, onClick = { onItemClick(item.key) })
        }
    }
}

@Composable
private fun HomeItemCard(item: HomeItem, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
