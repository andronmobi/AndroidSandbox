package fr.dappli.nostrongskipping.page

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
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MemoizationPage() {
    var recompositions by remember { mutableIntStateOf(0) }

    PageColumn(
        sample = {
            val unstableItems = remember { arrayListOf("Bob", "François", "Ivan") }
            val immutableItems = remember { listOf("John", "Olivier", "Karl").toImmutableList() }
            RecomposeParentButton(recompositions, onClick = { recompositions++ })

            LambdaComponent {
                println("items: $unstableItems")
            }
            HorizontalDivider()
            LambdaComponent {
                println("items: $immutableItems")
            }
        },
        code = "fr/dappli/nostrongskipping/page/MemoizationPage.kt",
        doc = { AssetMarkdown("nostrongskipping/Memoization.md") }
    )
}

@Composable
private fun LambdaComponent(lambda: () -> Unit) {
    lambda()
    Text(" - Compositions: ${rememberCompositionCount()}")
}
