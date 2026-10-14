package fr.dappli.androidsandbox.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object CompositionLocal : NavKey

@Serializable
data object StableImmutable : NavKey

@Serializable
data object NonStrongSkipping : NavKey

@Serializable
data object StrongSkipping : NavKey

@Serializable
data object PerformanceMistake1 : NavKey
