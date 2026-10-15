package fr.dappli.androidsandbox.compose.performance.mistake6

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn

@Composable
fun PerformanceMistake6Page(docPath: String) {
    PageColumn(
        sample = {
            val list = rememberItemsWithInsertButton()
            // ❌ insert at top → every item shifts index → all visible items recompose
            LazyColumn(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                items(list) { ItemRow(it) }
            }
        },
        code = "fr/dappli/androidsandbox/compose/performance/mistake6/PerformanceMistake6Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}
