package fr.dappli.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Counts how many times the calling composable has been (re)composed. */
@Composable
fun rememberCompositionCount(): Int {
    val count = remember { IntArray(1) }
    count[0]++
    return count[0]
}

/**
 * A scrollable page made of up to three sections: an optional live [sample] on top,
 * then the optional source file at [code] (an [AssetCode] path, collapsed by default),
 * then its [doc] (usually an [AssetMarkdown]).
 */
@Composable
fun PageColumn(
    modifier: Modifier = Modifier,
    sample: (@Composable ColumnScope.() -> Unit)? = null,
    code: String? = null,
    doc: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (sample != null) {
            PageSection(
                title = "Sample",
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                content = sample
            )
        }
        if (code != null) {
            // AssetCode draws its own code block container.
            PageSection(
                title = "Code",
                containerColor = null,
                collapsible = true,
            ) {
                AssetCode(code)
            }
        }
        PageSection(
            title = "Documentation",
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            content = doc
        )
    }
}

@Composable
private fun PageSection(
    title: String,
    containerColor: Color?,
    collapsible: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(!collapsible) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (collapsible) {
            SectionTitle(
                title = "$title ${if (expanded) "▾" else "▸"}",
                modifier = Modifier.clickable { expanded = !expanded }
            )
        } else {
            SectionTitle(title)
        }
        if (expanded) {
            if (containerColor == null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    content = content
                )
            } else {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = containerColor,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        content = content
                    )
                }
            }
        }
    }
}

/** Small uppercase heading shown above a section's card. */
@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun RecomposeParentButton(recompositions: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Recompose parent ($recompositions)")
    }
}
