package fr.dappli.androidsandbox.compose.compositionlocal

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import fr.dappli.common.AssetMarkdown
import fr.dappli.common.PageColumn
import fr.dappli.common.RecomposeParentButton
import fr.dappli.common.rememberCompositionCount

private const val DEFAULT_COUNTER_IF_NOT_PROVIDED = 0

// doc/ is packaged as an assets directory (see app/build.gradle.kts).
private const val DOC_ASSET = "CompositionLocal.md"

// Dynamic: when the provided value changes, only composables that read `.current` recompose.
private val LocalDynamicCounter = compositionLocalOf { DEFAULT_COUNTER_IF_NOT_PROVIDED }

// Static: when the provided value changes, the whole content of the provider recomposes,
// including children that never read `.current`. Cheaper to read, so use it for values
// that rarely or never change (e.g. theme, configuration, dependencies).
private val LocalStaticCounter = staticCompositionLocalOf { DEFAULT_COUNTER_IF_NOT_PROVIDED }

@Composable
fun CompositionLocalScreen(modifier: Modifier = Modifier) {
    PageColumn(
        modifier = modifier,
        sample = {
            var recompositions by remember { mutableIntStateOf(0) }
            RecomposeParentButton(recompositions, onClick = { recompositions++ })

            CompositionLocalProvider(LocalDynamicCounter provides recompositions) {
                MyComponent("compositionLocalOf")
                println("counter = ${LocalDynamicCounter.current}")
            }

            HorizontalDivider()

            CompositionLocalProvider(LocalStaticCounter provides recompositions) {
                MyComponent("staticCompositionLocalOf")
                println("counter = ${LocalStaticCounter.current}")
            }
        },
        code = "fr/dappli/androidsandbox/compose/compositionlocal/CompositionLocalScreen.kt",
        doc = { AssetMarkdown(DOC_ASSET) }
    )
}


@Composable
private fun MyComponent(name: String) {
    val composition = rememberCompositionCount()
    Text("with $name")
    Text("composition = $composition")
}
