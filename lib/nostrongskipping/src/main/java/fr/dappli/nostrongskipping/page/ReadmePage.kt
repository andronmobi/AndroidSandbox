package fr.dappli.nostrongskipping.page

import androidx.compose.runtime.Composable
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn

@Composable
internal fun ReadmePage() {
    PageColumn { AssetMarkdown("nostrongskipping/README.md") }
}
