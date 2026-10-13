package fr.dappli.androidsandbox.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


/** Counts how many times the calling composable has been (re)composed. */
@Composable
fun rememberCompositionCount(): Int {
    val count = remember { IntArray(1) }
    count[0]++
    return count[0]
}
