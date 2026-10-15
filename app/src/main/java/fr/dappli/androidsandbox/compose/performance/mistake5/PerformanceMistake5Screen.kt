package fr.dappli.androidsandbox.compose.performance.mistake5

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

private enum class Mistake5Tab(val title: String) {
    Mistake("Mistake"),
    Fix("Fix"),
}

/** Tabs showing the mistake (onClick captures a new list) and its fix (onClick captures only the view model). */
@Composable
fun PerformanceMistake5Screen(modifier: Modifier = Modifier) {
    val tabs = Mistake5Tab.entries
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
                Mistake5Tab.Mistake -> PerformanceMistake5Page("performance/Mistake5.md")
                Mistake5Tab.Fix -> PerformanceFix5Page("performance/Fix5.md")
            }
        }
    }
}
