package fr.dappli.androidsandbox.compose.performance.mistake5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount

@Composable
fun PerformanceMistake5Page(
    docPath: String,
    viewModel: MyViewModel = viewModel(key = "PerformanceMistake5Page"),
) {
    PageColumn(
        sample = { Screen(viewModel) },
        code = "fr/dappli/androidsandbox/compose/performance/mistake5/PerformanceMistake5Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen(viewModel: MyViewModel) {
    val state by viewModel.state.collectAsState()
    ButtonToUpdateState(state.updates, onClick = viewModel::update)
    Text("Screen compositions: ${rememberCompositionCount()}")
    CheckableItems(state.items, onToggle = viewModel::toggle)
    // ❌ `selected` is a new list every recomposition → onClick lambda is new → GarbageRow recomposes
    val selected = state.items.filter { it.checked }
    GarbageRow(onClick = { viewModel.delete(selected) })
}
