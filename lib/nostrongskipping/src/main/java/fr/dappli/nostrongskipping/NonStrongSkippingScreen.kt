package fr.dappli.nostrongskipping

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
import fr.dappli.nostrongskipping.page.ArrayListParameterPage
import fr.dappli.nostrongskipping.page.ArrayListPropertyPage
import fr.dappli.nostrongskipping.page.ListParameterPage
import fr.dappli.nostrongskipping.page.MemoizationPage
import fr.dappli.nostrongskipping.page.ReadmePage
import fr.dappli.nostrongskipping.page.VarPropertyPage
import kotlinx.coroutines.launch

private enum class NonStrongSkippingPage(val title: String) {
    Readme("README"),
    VarProperty("Var property"),
    ArrayListParameter("ArrayList parameter"),
    ListParameter("List parameter"),
    ArrayListProperty("ArrayList property"),
    Memoization("Memoization"),
}

@Composable
fun NonStrongSkippingScreen(modifier: Modifier = Modifier) {
    val pages = NonStrongSkippingPage.entries
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    Column(modifier = modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(selectedTabIndex = pagerState.currentPage, edgePadding = 0.dp) {
            pages.forEach { page ->
                Tab(
                    selected = pagerState.currentPage == page.ordinal,
                    onClick = { scope.launch { pagerState.animateScrollToPage(page.ordinal) } },
                    text = { Text(page.title) }
                )
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            when (pages[index]) {
                NonStrongSkippingPage.Readme -> ReadmePage()
                NonStrongSkippingPage.VarProperty -> VarPropertyPage()
                NonStrongSkippingPage.ArrayListParameter -> ArrayListParameterPage()
                NonStrongSkippingPage.ListParameter -> ListParameterPage()
                NonStrongSkippingPage.ArrayListProperty -> ArrayListPropertyPage()
                NonStrongSkippingPage.Memoization -> MemoizationPage()
            }
        }
    }
}
