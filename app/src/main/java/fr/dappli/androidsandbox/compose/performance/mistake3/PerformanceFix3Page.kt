package fr.dappli.androidsandbox.compose.performance.mistake3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount

@Composable
fun PerformanceFix3Page(
    docPath: String,
    viewModel: MyViewModel = viewModel(key = "PerformanceFix3Page"),
) {
    PageColumn(
        sample = { Screen(viewModel) },
        code = "fr/dappli/androidsandbox/compose/performance/mistake3/PerformanceFix3Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen(viewModel: MyViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    LoadingControls(uiState.isLoading, onToggleLoading = viewModel::toggleLoading)
    Text("Screen compositions: ${rememberCompositionCount()}")
    // ✅ only depends on what it uses
    ItemList(items = uiState.items)
}

/**
 * With strong skipping, List parameters are fine for performance.
 */
@Composable
private fun ItemList(items: List<UiItem>) {
    Column {
        Text("ItemList compositions: ${rememberCompositionCount()}")
        items.forEach {
            Text(" - ${it.label}")
        }
    }
}
