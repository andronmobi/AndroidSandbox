package fr.dappli.androidsandbox.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Player : NavKey

@Serializable
data class Playback(val streamId: String) : NavKey
