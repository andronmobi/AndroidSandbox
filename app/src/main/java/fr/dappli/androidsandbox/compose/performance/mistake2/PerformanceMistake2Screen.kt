package fr.dappli.androidsandbox.compose.performance.mistake2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.launch

private enum class Mistake2Tab(val title: String) {
    Mistake("Mistake"),
    Fix("Fix"),
}

/** Tabs showing the mistake (items re-mapped on every emission) and its fix (items mapped on their own flow). */
@Composable
fun PerformanceMistake2Screen(modifier: Modifier = Modifier) {
    val tabs = Mistake2Tab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    // Leaving a page with a focused search field would keep the keyboard open over the next page.
    LaunchedEffect(pagerState.currentPage) {
        focusManager.clearFocus()
    }
    Column(modifier = modifier.fillMaxSize()) {
        PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEach { tab ->
                Tab(
                    selected = pagerState.currentPage == tab.ordinal,
                    onClick = { scope.launch { pagerState.animateScrollToPage(tab.ordinal) } },
                    text = { Text(tab.title) }
                )
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            when (tabs[index]) {
                Mistake2Tab.Mistake -> PerformanceMistake2Page("performance/Mistake2.md")
                Mistake2Tab.Fix -> PerformanceFix2Page("performance/Fix2.md")
            }
        }
    }
}
