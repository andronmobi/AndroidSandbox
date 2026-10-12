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
fun ArrayListParameterPage() {
    var recompositions by remember { mutableIntStateOf(0) }
    val items = remember { arrayListOf("Bob", "François", "Ivan") }
    PageColumn {
        RecomposeParentButton(recompositions, onClick = { recompositions++ })
        LambdaComponent(items)

        HorizontalDivider()
        AssetMarkdown("nostrongskipping/ArrayListParameter.md")
    }
}

@Suppress("ParamsComparedByRef")
@Composable
private fun LambdaComponent(items: ArrayList<String>) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        items.forEach {
            Text(" - $it")
        }
    }
}
