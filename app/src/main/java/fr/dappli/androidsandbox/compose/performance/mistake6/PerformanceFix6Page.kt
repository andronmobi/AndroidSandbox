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
fun PerformanceFix6Page(docPath: String) {
    PageColumn(
        sample = {
            val list = rememberItemsWithInsertButton()
            // ✅ items are tracked by identity
            LazyColumn(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                items(list, key = { it.id }) { ItemRow(it) }
            }
        },
        code = "fr/dappli/androidsandbox/compose/performance/mistake6/PerformanceFix6Page.kt",
        doc = { AssetMarkdown(docPath) }
    )
}
