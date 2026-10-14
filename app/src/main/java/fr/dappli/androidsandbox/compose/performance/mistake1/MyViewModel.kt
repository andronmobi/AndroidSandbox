package fr.dappli.androidsandbox.compose.performance.mistake1

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Item(val name: String, val visible: Boolean)

data class UiState(val items: List<Item>, val updates: Int = 0)

@Stable
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        UiState(
            items = listOf(
                Item("Bob", visible = true),
                Item("François", visible = false),
                Item("Ivan", visible = true),
                Item("John", visible = true),
            )
        )
    )
    val state: StateFlow<UiState> = _state.asStateFlow()

    /** Emits a new state that keeps the same items list, so only [UiState.updates] changes. */
    fun update() {
        _state.update { it.copy(updates = it.updates + 1) }
    }
}
