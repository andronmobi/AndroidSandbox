package fr.dappli.androidsandbox.compose.performance.mistake2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import fr.dappli.common.rememberCompositionCount
import kotlinx.coroutines.flow.MutableStateFlow

data class Item(val name: String)

data class UiItem(val label: String)

fun Item.toUi() = UiItem(label = name.uppercase())

data class UiState(
    val items: List<UiItem> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
)


@Composable
fun SearchControls(
    query: String,
    isLoading: Boolean,
    onQueryChange: (String) -> Unit,
    onToggleLoading: () -> Unit,
) {
    OutlinedTextField(value = query, onValueChange = onQueryChange, label = { Text("Search") })
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = onToggleLoading) {
            Text("Toggle loading")
        }
        Text("isLoading: $isLoading")
    }
}

@Composable
fun ItemList(items: List<UiItem>) {
    Column {
        Text("ItemList compositions: ${rememberCompositionCount()}")
        items.forEach {
            Text(" - ${it.label}")
        }
    }
}
