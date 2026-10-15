package fr.dappli.androidsandbox.compose.performance.mistake4

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/** A scrollable list with a "scroll to top" button shown when [showFab] is true. */
@Composable
fun ItemsWithFab(listState: LazyListState, showFab: Boolean) {
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(50) { index ->
                Text("Item $index", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
        if (showFab) {
            SmallFloatingActionButton(
                onClick = { scope.launch { listState.animateScrollToItem(0) } },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text("↑")
            }
        }
    }
}

/** An "Animate" button that slides the returned offset (in dp) to 200 and back. */
@Composable
fun rememberOffsetAnimation(): State<Float> {
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            offset.animateTo(200f, tween(1000))
            offset.animateTo(0f, tween(1000))
        }
    }) {
        Text("Animate")
    }
    return offset.asState()
}
