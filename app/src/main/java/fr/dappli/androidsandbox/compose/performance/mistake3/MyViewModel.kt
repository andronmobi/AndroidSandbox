package fr.dappli.androidsandbox.compose.performance.mistake3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiItem(val label: String)

data class UiState(
    val items: List<UiItem>,
    val isLoading: Boolean = false,
)

@Stable
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        UiState(items = listOf(UiItem("BOB"), UiItem("FRANÇOIS"), UiItem("IVAN"), UiItem("JOHN")))
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /** Copies the state with a new loading flag: a new [UiState] that keeps the same items list. */
    fun toggleLoading() {
        _uiState.update { it.copy(isLoading = !it.isLoading) }
    }
}

@Composable
fun LoadingControls(isLoading: Boolean, onToggleLoading: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = onToggleLoading) {
            Text("Toggle loading")
        }
        Text("isLoading: $isLoading")
    }
}
