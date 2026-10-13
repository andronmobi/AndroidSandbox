package fr.dappli.nostrongskipping.page

import androidx.compose.runtime.Composable
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn

@Composable
fun ReadmePage(docPath: String) {
    PageColumn { AssetMarkdown(docPath) }
}
