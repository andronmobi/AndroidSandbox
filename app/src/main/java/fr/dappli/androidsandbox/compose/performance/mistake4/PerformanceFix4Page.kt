package fr.dappli.androidsandbox.compose.performance.mistake4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount

@Composable
fun PerformanceFix4Page(docPath: String) {
    PageColumn(
        sample = {
            Screen()
            HorizontalDivider()
            val offsetState = rememberOffsetAnimation()
            MovingBox(offsetState)
        },
        code = "fr/dappli/androidsandbox/compose/performance/mistake4/PerformanceFix4Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

@Composable
private fun Screen() {
    val listState = rememberLazyListState()
    // ✅ recomposes only when the boolean flips
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    Text("Screen compositions: ${rememberCompositionCount()}")
    ItemsWithFab(listState, showFab)
}

@Composable
private fun MovingBox(offsetState: State<Float>) {
    Text("MovingBox compositions: ${rememberCompositionCount()}")
    Box(
        Modifier
            // ✅ reads the offset in the layout phase only
            .offset { IntOffset(offsetState.value.dp.roundToPx(), 0) }
            .size(40.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}
