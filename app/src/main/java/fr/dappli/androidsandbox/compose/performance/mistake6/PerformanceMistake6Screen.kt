package fr.dappli.androidsandbox.compose.performance.mistake6

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

private enum class Mistake6Tab(val title: String) {
    Mistake("Mistake"),
    Fix("Fix"),
}

/** Tabs showing the mistake (LazyColumn items without keys) and its fix (items keyed by id). */
@Composable
fun PerformanceMistake6Screen(modifier: Modifier = Modifier) {
    val tabs = Mistake6Tab.entries
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
                Mistake6Tab.Mistake -> PerformanceMistake6Page("performance/Mistake6.md")
                Mistake6Tab.Fix -> PerformanceFix6Page("performance/Fix6.md")
            }
        }
    }
}
