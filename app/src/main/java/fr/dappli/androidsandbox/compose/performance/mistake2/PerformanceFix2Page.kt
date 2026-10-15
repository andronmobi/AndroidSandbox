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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
internal class Fix2ViewModel : ViewModel() {
    private val itemsFlow = MutableStateFlow(listOf(Item("Bob"), Item("François"), Item("Ivan"), Item("John")))
    private val queryFlow = MutableStateFlow("")
    private val loadingFlow = MutableStateFlow(false)

    /** How many times the items have been mapped to UI items. */
    var mappings = 0
        private set

    // ✅ map items only when the source items change
    private val uiItems = itemsFlow.map { list ->
        mappings++
        list.map { it.toUi() }
    }

    val uiState: StateFlow<UiState> = combine(
        uiItems, queryFlow, loadingFlow
    ) { items, query, loading ->
        UiState(items, query, loading)  // same `items` reference when only query changes
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState())

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun toggleLoading() {
        loadingFlow.value = !loadingFlow.value
    }
}

@Composable
fun PerformanceFix2Page(docPath: String) {
    val viewModel: Fix2ViewModel = viewModel()
    PageColumn(
        sample = { Screen(viewModel) },
        code = "fr/dappli/androidsandbox/compose/performance/mistake2/PerformanceFix2Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen(viewModel: Fix2ViewModel) {
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
