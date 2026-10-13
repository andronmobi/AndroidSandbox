package fr.dappli.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography

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
    // The library's default headings are display-sized; scale them down to fit inside a card.
    val typography = MaterialTheme.typography
    Markdown(
        content = content,
        typography = markdownTypography(
            h1 = typography.headlineSmall,
            h2 = typography.titleLarge,
            h3 = typography.titleMedium,
            h4 = typography.titleSmall,
            h5 = typography.titleSmall,
            h6 = typography.titleSmall,
        ),
        modifier = modifier.fillMaxWidth()
    )
}
