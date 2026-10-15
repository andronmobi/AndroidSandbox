package fr.dappli.androidsandbox.compose.performance.mistake5

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.dappli.androidsandbox.R
import fr.dappli.common.rememberCompositionCount

@Composable
fun ButtonToUpdateState(updates: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Emit new state ($updates)")
    }
}

@Composable
fun CheckableItems(items: List<Item>, onToggle: (Item) -> Unit) {
    Column {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = item.checked, onCheckedChange = { onToggle(item) })
                Text(item.name)
            }
        }
    }
}

/** A clickable row: it can only skip when it receives the same [onClick] lambda. */
@Composable
fun GarbageRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 12.dp).clickable(onClick = onClick),
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = "Delete checked",
            tint = MaterialTheme.colorScheme.error
        )
        Text("GarbageRow compositions: ${rememberCompositionCount()}")
    }
}
