package fr.dappli.androidsandbox.compose.performance.mistake4

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

private enum class Mistake4Tab(val title: String) {
    Mistake("Mistake"),
    Fix("Fix"),
}

/** Tabs showing the mistake (fast-changing state read in composition) and its fix (derivedStateOf and a deferred read). */
@Composable
fun PerformanceMistake4Screen(modifier: Modifier = Modifier) {
    val tabs = Mistake4Tab.entries
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
                Mistake4Tab.Mistake -> PerformanceMistake4Page("performance/Mistake4.md")
                Mistake4Tab.Fix -> PerformanceFix4Page("performance/Fix4.md")
            }
        }
    }
}
