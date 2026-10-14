package fr.dappli.androidsandbox.compose.performance.mistake1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.dappli.common.rememberCompositionCount

@Composable
fun UpdateStateButton(updates: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Emit new state ($updates)")
    }
}

@Composable
fun ItemList(items: List<Item>) {
    Column {
        Text("ItemList compositions: ${rememberCompositionCount()}")
        items.forEach {
            Text(" - ${it.name}")
        }
    }
}
