package fr.dappli.androidsandbox.compose.performance.mistake4

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.rememberCompositionCount

@Composable
fun PerformanceMistake4Page(docPath: String) {
    PageColumn(
        sample = {
            Screen()
            HorizontalDivider()
            val offsetState = rememberOffsetAnimation()
            MovingBox(offsetState)
        },
        code = "fr/dappli/androidsandbox/compose/performance/mistake4/PerformanceMistake4Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}

// ❌ reads scroll position in composition → Screen recomposes whenever the index changes
@SuppressLint("FrequentlyChangingValue")
@Composable
private fun Screen() {
    val listState = rememberLazyListState()
    val showFab = listState.firstVisibleItemIndex > 0
    Text("Screen compositions: ${rememberCompositionCount()}")
    ItemsWithFab(listState, showFab)
}

@Composable
private fun MovingBox(offsetState: State<Float>) {
    Text("MovingBox compositions: ${rememberCompositionCount()}")
    Box(
        Modifier
            // ❌ reads the offset in composition → recomposition every frame
            .offset(x = offsetState.value.dp)
            .size(40.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}
