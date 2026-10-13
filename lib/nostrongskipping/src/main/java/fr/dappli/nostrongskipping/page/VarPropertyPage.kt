package fr.dappli.nostrongskipping.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.RecomposeParentButton
import fr.dappli.common.rememberCompositionCount

// Unstable type
data class UnstablePerson(var name: String)

@Stable
data class StablePerson(var name: String)

@Composable
fun VarPropertyPage(docPath: String) {
    var recompositions by remember { mutableIntStateOf(0) }
    PageColumn(
        sample = {
            RecomposeParentButton(recompositions, onClick = { recompositions++ })

            LambdaComponent(UnstablePerson("Bob"))
            HorizontalDivider()
            StableComponent(StablePerson("Frank"))
        },
        code = "fr/dappli/nostrongskipping/page/VarPropertyPage.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Suppress("ParamsComparedByRef")
@Composable
private fun LambdaComponent(unstablePerson: UnstablePerson) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        Text(" - ${unstablePerson.name}")
    }
}

@Composable
private fun StableComponent(stablePerson: StablePerson) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        Text(" - ${stablePerson.name}")
    }
}
