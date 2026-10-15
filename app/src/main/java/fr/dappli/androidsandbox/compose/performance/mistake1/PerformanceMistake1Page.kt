package fr.dappli.androidsandbox.compose.performance.mistake1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount

@Composable
fun PerformanceMistake1Page(
    docPath: String,
    viewModel: MyViewModel = viewModel(key = "PerformanceMistake1Page"),
) {
    PageColumn(
        sample = { Screen(viewModel) },
        code = "fr/dappli/androidsandbox/compose/performance/mistake1/PerformanceMistake1Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen(viewModel: MyViewModel) {
    val state by viewModel.state.collectAsState()
    UpdateStateButton(state.updates, onClick = viewModel::update)
    Text("Screen compositions: ${rememberCompositionCount()}")
    val filterRuns = remember { IntArray(1) }
    // ❌ new list on every recomposition of Screen
    ItemList(items = state.items.filter { it.visible }.also { filterRuns[0]++ })
    Text("filter runs: ${filterRuns[0]}")
}
