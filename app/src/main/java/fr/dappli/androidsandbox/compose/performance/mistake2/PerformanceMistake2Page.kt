package fr.dappli.androidsandbox.compose.performance.mistake2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@Stable
internal class Mistake2ViewModel : ViewModel() {

    private val itemsFlow = MutableStateFlow(listOf(Item("Bob"), Item("François"), Item("Ivan"), Item("John")))
    private val queryFlow = MutableStateFlow("")
    private val loadingFlow = MutableStateFlow(false)

    /** How many times the items have been mapped to UI items. */
    var mappings = 0
        private set

    // ❌ items are re-mapped every time `query` OR `isLoading` emits
    val uiState: StateFlow<UiState> = combine(
        itemsFlow, queryFlow, loadingFlow
    ) { items, query, loading ->
        mappings++
        UiState(
            items = items.map { it.toUi() },   // new list every emission
            query = query,
            isLoading = loading
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState())

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun toggleLoading() {
        loadingFlow.value = !loadingFlow.value
    }
}

@Composable
fun PerformanceMistake2Page(docPath: String) {
    val viewModel: Mistake2ViewModel = viewModel()
    PageColumn(
        sample = { Screen(viewModel) },
        code = "fr/dappli/androidsandbox/compose/performance/mistake2/PerformanceMistake2Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen(viewModel: Mistake2ViewModel) {
    val state by viewModel.uiState.collectAsState()
    SearchControls(
        query = state.query,
        isLoading = state.isLoading,
        onQueryChange = viewModel::onQueryChange,
        onToggleLoading = viewModel::toggleLoading,
    )
    Text("Screen compositions: ${rememberCompositionCount()}")
    Text("Item mappings: ${viewModel.mappings}")
    ItemList(items = state.items)
}
