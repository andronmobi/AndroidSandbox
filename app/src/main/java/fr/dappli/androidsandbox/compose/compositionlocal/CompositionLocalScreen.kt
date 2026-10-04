package fr.dappli.androidsandbox.compose.compositionlocal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme

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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var counter by remember { mutableIntStateOf(1) }
        Button(onClick = { counter++ }) {
            Text("Update provider")
        }

        CompositionLocalProvider(LocalDynamicCounter provides counter) {
            MyComponent("compositionLocalOf")
            Text("counter = ${LocalDynamicCounter.current}")
        }

        HorizontalDivider()

        CompositionLocalProvider(LocalStaticCounter provides counter) {
            MyComponent("staticCompositionLocalOf")
            Text("counter = ${LocalStaticCounter.current}")
        }

        val context = LocalContext.current
        val doc = remember {
            context.assets.open(DOC_ASSET).bufferedReader().use { it.readText() }
        }
        HorizontalDivider()
        Markdown(content = doc, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
    }
}


@Composable
private fun MyComponent(name: String) {
    val composition = rememberCompositionCount()
    Text("with $name")
    Text("composition = $composition")
}

/** Counts how many times the calling composable has been (re)composed. */
@Composable
private fun rememberCompositionCount(): Int {
    val count = remember { IntArray(1) }
    count[0]++
    return count[0]
}

@Preview(showBackground = true)
@Composable
fun CompositionLocalScreenPreview() {
    AndroidSandboxTheme {
        CompositionLocalScreen()
    }
}
