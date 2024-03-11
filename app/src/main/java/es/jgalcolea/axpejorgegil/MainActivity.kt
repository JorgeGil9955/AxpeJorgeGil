package es.jgalcolea.axpejorgegil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import es.jgalcolea.axpejorgegil.ui.ContactNavigation
import es.jgalcolea.axpejorgegil.ui.screens.ContactDetail
import es.jgalcolea.axpejorgegil.ui.screens.ContactList
import es.jgalcolea.axpejorgegil.ui.theme.AxpeJorgeGilTheme
import es.jgalcolea.axpejorgegil.viewmodel.SharedContactViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AxpeJorgeGilTheme {
                Surface (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val mViewModel: SharedContactViewModel by viewModels()
                    ContactNavigation(sharedViewModel = mViewModel)
                }
            }
        }
    }
}