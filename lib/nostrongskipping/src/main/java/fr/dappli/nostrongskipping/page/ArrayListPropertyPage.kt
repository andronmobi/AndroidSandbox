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
import fr.dappli.common.PageColumn
import fr.dappli.common.RecomposeParentButton
import fr.dappli.common.rememberCompositionCount

// Unstable type
data class UnstablePersons(val names: ArrayList<String>)

@Stable
data class StablePersons(val names: ArrayList<String>)

@Composable
fun ArrayListPropertyPage() {
    var recompositions by remember { mutableIntStateOf(0) }
    val unstablePersons = remember { UnstablePersons(arrayListOf("Bob", "François", "Ivan")) }
    val stablePersons = remember { StablePersons(arrayListOf("John", "Olivier", "Karl")) }
    PageColumn {
        RecomposeParentButton(recompositions, onClick = { recompositions++ })

        LambdaComponent(unstablePersons)
        HorizontalDivider()
        StableComponent(stablePersons)
    }
}

@Suppress("ParamsComparedByRef")
@Composable
private fun LambdaComponent(persons: UnstablePersons) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        persons.names.forEach {
            Text(" - $it")
        }
    }
}

@Composable
private fun StableComponent(persons: StablePersons) {
    Column {
        Text("Compositions: ${rememberCompositionCount()}")
        persons.names.forEach {
            Text(" - $it")
        }
    }
}
