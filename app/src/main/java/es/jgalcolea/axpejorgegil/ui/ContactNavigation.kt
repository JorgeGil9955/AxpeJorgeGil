package es.jgalcolea.axpejorgegil.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.jgalcolea.axpejorgegil.ui.screens.ContactDetail
import es.jgalcolea.axpejorgegil.ui.screens.ContactList
import es.jgalcolea.axpejorgegil.viewmodel.SharedContactViewModel

@Composable
fun ContactNavigation(sharedViewModel: SharedContactViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ContactList") {
        composable("ContactList") { ContactList(navController = navController, viewModel = sharedViewModel) }
        composable("ContactDetail") { ContactDetail(navController = navController, viewModel = sharedViewModel) }
    }
}