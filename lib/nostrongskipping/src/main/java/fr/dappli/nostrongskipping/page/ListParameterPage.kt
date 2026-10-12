package fr.dappli.nostrongskipping.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.RecomposeParentButton
import fr.dappli.common.rememberCompositionCount

@Composable
fun ListParameterPage() {
    var recompositions by remember { mutableIntStateOf(0) }
    val unstableItems = remember { arrayListOf("Bob", "François", "Ivan") }
    val immutableItems = remember { listOf("John", "Olivier", "Karl") }
    PageColumn {
        RecomposeParentButton(recompositions, onClick = { recompositions++ })

        UnknownStabilityComponent(unstableItems)
        HorizontalDivider()
        UnknownStabilityComponent(immutableItems)

        HorizontalDivider()
        AssetMarkdown("nostrongskipping/ListParameter.md")
    }
}

@Composable
private fun UnknownStabilityComponent(items: List<String>) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        items.forEach {
            Text(" - $it")
        }
    }
}
