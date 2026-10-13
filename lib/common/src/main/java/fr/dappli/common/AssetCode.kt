package fr.dappli.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.highlightedCodeFence
import com.mikepenz.markdown.m3.Markdown

/**
 * Renders a Kotlin source file from the app's assets with syntax highlighting.
 * Library modules ship their src/main/java as assets (see their build.gradle.kts),
 * so [path] is the file's package path, e.g. "fr/dappli/foo/FooPage.kt".
 * The package declaration and imports are left out.
 */
@Composable
fun AssetCode(path: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val content = remember(path) {
        val source = context.assets.open(path).bufferedReader().use { it.readText() }
        val body = source.lines()
            .dropWhile { it.isBlank() || it.startsWith("package ") || it.startsWith("import ") }
            .joinToString("\n")
            .trimEnd()
        "```kotlin\n$body\n```"
    }
    Markdown(
        content = content,
        components = markdownComponents(codeFence = highlightedCodeFence),
        modifier = modifier.fillMaxWidth()
    )
}
