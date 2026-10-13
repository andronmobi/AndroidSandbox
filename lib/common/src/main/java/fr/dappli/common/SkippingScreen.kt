package fr.dappli.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/** A skipping page: the composable receives the [AssetMarkdown] path of its doc. */
typealias SkippingPage = @Composable (docPath: String) -> Unit

private enum class SkippingTab(val title: String) {
    Readme("README"),
    VarProperty("Var property"),
    ArrayListParameter("ArrayList parameter"),
    ListParameter("List parameter"),
    ArrayListProperty("ArrayList property"),
    Memoization("Memoization"),
}

/**
 * Tabs showing the skipping pages. Skipping is decided when a page is compiled,
 * so each caller passes the pages compiled by its own module (with or without
 * strong skipping) and the docs that describe that mode.
 */
@Composable
fun SkippingScreen(
    readmeDoc: String,
    varPropertyDoc: String,
    arrayListParameterDoc: String,
    listParameterDoc: String,
    arrayListPropertyDoc: String,
    memoizationDoc: String,
    readme: SkippingPage,
    varProperty: SkippingPage,
    arrayListParameter: SkippingPage,
    listParameter: SkippingPage,
    arrayListProperty: SkippingPage,
    memoization: SkippingPage,
    modifier: Modifier = Modifier,
) {
    val tabs = SkippingTab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    Column(modifier = modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(selectedTabIndex = pagerState.currentPage, edgePadding = 0.dp) {
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
                SkippingTab.Readme -> readme(readmeDoc)
                SkippingTab.VarProperty -> varProperty(varPropertyDoc)
                SkippingTab.ArrayListParameter -> arrayListParameter(arrayListParameterDoc)
                SkippingTab.ListParameter -> listParameter(listParameterDoc)
                SkippingTab.ArrayListProperty -> arrayListProperty(arrayListPropertyDoc)
                SkippingTab.Memoization -> memoization(memoizationDoc)
            }
        }
    }
}
