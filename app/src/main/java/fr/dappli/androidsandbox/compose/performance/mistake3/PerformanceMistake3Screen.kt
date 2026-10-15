package fr.dappli.androidsandbox.compose.performance.mistake3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

private enum class Mistake3Tab(val title: String) {
    Mistake("Mistake"),
    Fix("Fix"),
}

/** Tabs showing the mistake (ItemList takes the whole state) and its fix (ItemList takes only the items). */
@Composable
fun PerformanceMistake3Screen(modifier: Modifier = Modifier) {
    val tabs = Mistake3Tab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
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
                Mistake3Tab.Mistake -> PerformanceMistake3Page("performance/Mistake3.md")
                Mistake3Tab.Fix -> PerformanceFix3Page("performance/Fix3.md")
            }
        }
    }
}
