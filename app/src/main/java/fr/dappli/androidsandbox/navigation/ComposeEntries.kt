package fr.dappli.androidsandbox.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import fr.dappli.androidsandbox.compose.ComposeScreen
import fr.dappli.androidsandbox.compose.compositionlocal.CompositionLocalScreen
import fr.dappli.androidsandbox.compose.performance.mistake1.PerformanceMistake1Screen
import fr.dappli.androidsandbox.compose.performance.mistake2.PerformanceMistake2Screen
import fr.dappli.androidsandbox.compose.performance.mistake3.PerformanceMistake3Screen
import fr.dappli.androidsandbox.compose.performance.mistake4.PerformanceMistake4Screen
import fr.dappli.androidsandbox.compose.performance.mistake5.PerformanceMistake5Screen
import fr.dappli.androidsandbox.compose.performance.mistake6.PerformanceMistake6Screen
import fr.dappli.androidsandbox.compose.strongskipping.page.ArrayListParameterPage as StrongArrayListParameterPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ArrayListPropertyPage as StrongArrayListPropertyPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ListParameterPage as StrongListParameterPage
import fr.dappli.androidsandbox.compose.strongskipping.page.MemoizationPage as StrongMemoizationPage
import fr.dappli.androidsandbox.compose.strongskipping.page.ReadmePage as StrongReadmePage
import fr.dappli.androidsandbox.compose.strongskipping.page.VarPropertyPage as StrongVarPropertyPage
import fr.dappli.common.SkippingScreen
import fr.dappli.nostrongskipping.page.ArrayListParameterPage
import fr.dappli.nostrongskipping.page.ArrayListPropertyPage
import fr.dappli.nostrongskipping.page.ListParameterPage
import fr.dappli.nostrongskipping.page.MemoizationPage
import fr.dappli.nostrongskipping.page.ReadmePage
import fr.dappli.nostrongskipping.page.VarPropertyPage

fun EntryProviderScope<NavKey>.composeEntries(onNavigate: (NavKey) -> Unit) {
    entry<Compose> {
        ComposeScreen(onItemClick = onNavigate)
    }
    entry<CompositionLocal> {
        CompositionLocalScreen()
    }
    entry<NonStrongSkipping> {
        SkippingScreen(
            readmeDoc = "nostrongskipping/README.md",
            varPropertyDoc = "nostrongskipping/VarProperty.md",
            arrayListParameterDoc = "nostrongskipping/ArrayListParameter.md",
            listParameterDoc = "nostrongskipping/ListParameter.md",
            arrayListPropertyDoc = "nostrongskipping/ArrayListProperty.md",
            memoizationDoc = "nostrongskipping/Memoization.md",
            readme = { ReadmePage(it) },
            varProperty = { VarPropertyPage(it) },
            arrayListParameter = { ArrayListParameterPage(it) },
            listParameter = { ListParameterPage(it) },
            arrayListProperty = { ArrayListPropertyPage(it) },
            memoization = { MemoizationPage(it) },
        )
    }
    entry<StrongSkipping> {
        SkippingScreen(
            readmeDoc = "strongskipping/README.md",
            varPropertyDoc = "strongskipping/VarProperty.md",
            arrayListParameterDoc = "strongskipping/ArrayListParameter.md",
            listParameterDoc = "strongskipping/ListParameter.md",
            arrayListPropertyDoc = "strongskipping/ArrayListProperty.md",
            memoizationDoc = "strongskipping/Memoization.md",
            readme = { StrongReadmePage(it) },
            varProperty = { StrongVarPropertyPage(it) },
            arrayListParameter = { StrongArrayListParameterPage(it) },
            listParameter = { StrongListParameterPage(it) },
            arrayListProperty = { StrongArrayListPropertyPage(it) },
            memoization = { StrongMemoizationPage(it) },
        )
    }
    entry<PerformanceMistake1> {
        PerformanceMistake1Screen()
    }
    entry<PerformanceMistake2> {
        PerformanceMistake2Screen()
    }
    entry<PerformanceMistake3> {
        PerformanceMistake3Screen()
    }
    entry<PerformanceMistake4> {
        PerformanceMistake4Screen()
    }
    entry<PerformanceMistake5> {
        PerformanceMistake5Screen()
    }
    entry<PerformanceMistake6> {
        PerformanceMistake6Screen()
    }
}
