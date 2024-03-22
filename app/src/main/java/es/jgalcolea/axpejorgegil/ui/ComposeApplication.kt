package es.jgalcolea.axpejorgegil.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import es.jgalcolea.axpejorgegil.ui.theme.AxpeJorgeGilTheme

@Composable
fun ComposeApplication() {
    AxpeJorgeGilTheme {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ContactNavigation()
        }
    }
}