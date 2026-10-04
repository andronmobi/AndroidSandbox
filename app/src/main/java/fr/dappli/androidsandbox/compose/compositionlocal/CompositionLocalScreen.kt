package fr.dappli.androidsandbox.compose.compositionlocal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.dappli.androidsandbox.ui.theme.AndroidSandboxTheme

val LocalUserName = compositionLocalOf { "Guest" }

@Composable
fun CompositionLocalScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserNameText()
        CompositionLocalProvider(LocalUserName provides "Andrei") {
            UserNameText()
        }
    }
}

@Composable
private fun UserNameText() {
    Text(text = "LocalUserName = ${LocalUserName.current}")
}

@Preview(showBackground = true)
@Composable
fun CompositionLocalScreenPreview() {
    AndroidSandboxTheme {
        CompositionLocalScreen()
    }
}
