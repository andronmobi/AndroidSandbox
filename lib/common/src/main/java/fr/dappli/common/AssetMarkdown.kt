package fr.dappli.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mikepenz.markdown.m3.Markdown

/**
 * Renders a markdown file from the app's assets.
 * The root doc/ directory is packaged as assets by :app (see app/build.gradle.kts),
 * so [path] is relative to doc/.
 */
@Composable
fun AssetMarkdown(path: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val content = remember(path) {
        context.assets.open(path).bufferedReader().use { it.readText() }
    }
    Markdown(content = content, modifier = modifier.fillMaxWidth())
}
