package fr.dappli.androidsandbox.compose.performance.mistake5

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Item(val name: String, val checked: Boolean = false)

data class UiState(val items: List<Item>, val updates: Int = 0)

@Stable
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        UiState(items = listOf(Item("Bob"), Item("François"), Item("Ivan"), Item("John")))
    )
    val state: StateFlow<UiState> = _state.asStateFlow()

    /** Emits a new state that keeps the same items list, so only [UiState.updates] changes. */
    fun update() {
        _state.update { it.copy(updates = it.updates + 1) }
    }

    fun toggle(item: Item) {
        _state.update { state ->
            state.copy(items = state.items.map { if (it == item) it.copy(checked = !it.checked) else it })
        }
    }

    fun delete(items: List<Item>) {
        _state.update { it.copy(items = it.items - items.toSet()) }
    }

    /** Computes the checked items at click time, from the current state. */
    fun deleteChecked() {
        _state.update { state -> state.copy(items = state.items.filterNot { it.checked }) }
    }
}
