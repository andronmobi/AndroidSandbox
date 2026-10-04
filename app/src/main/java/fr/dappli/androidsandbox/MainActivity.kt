package fr.dappli.androidsandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import fr.dappli.androidsandbox.compose.compositionlocal.CompositionLocalScreen
import fr.dappli.androidsandbox.navigation.CompositionLocal
import fr.dappli.androidsandbox.navigation.Home
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme

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
                        }
                    )
                }
            }
        }
    }
}

private val composeItems = listOf<Pair<String, NavKey>>(
    "CompositionLocal" to CompositionLocal,
)

private fun titleOf(key: NavKey): String? =
    composeItems.firstOrNull { it.second == key }?.first

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
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            ListItem(
                headlineContent = { Text("Compose", style = MaterialTheme.typography.titleLarge) }
            )
        }
        composeItems.forEach { (title, key) ->
            item {
                ListItem(
                    headlineContent = { Text(title, style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.clickable { onItemClick(key) }
                )
                HorizontalDivider()
            }
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
