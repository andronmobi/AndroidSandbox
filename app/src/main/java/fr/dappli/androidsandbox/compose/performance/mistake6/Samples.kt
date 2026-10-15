package fr.dappli.androidsandbox.compose.performance.mistake6

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.dappli.common.rememberCompositionCount

data class Item(val id: Int, val name: String)

/** A list of items with an "Insert at top" button that adds a new item before the others. */
@Composable
fun rememberItemsWithInsertButton(): List<Item> {
    var items by remember { mutableStateOf(List(5) { Item(id = it, name = "Item $it") }) }
    Button(onClick = {
        val id = items.size
        items = listOf(Item(id = id, name = "Item $id")) + items
    }) {
        Text("Insert at top")
    }
    return items
}

@Composable
fun ItemRow(item: Item) {
    Text(
        "${item.name}: compositions ${rememberCompositionCount()}",
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}
