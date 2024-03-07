package es.jgalcolea.axpejorgegil.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import es.jgalcolea.axpejorgegil.ui.components.InfoMapRow
import es.jgalcolea.axpejorgegil.ui.components.InfoRow
import es.jgalcolea.axpejorgegil.viewmodel.SharedContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetail(navController: NavController, viewModel: SharedContactViewModel) {
    val selectedContact = viewModel.selectedContact
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedContact.name,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            InfoRow(icon = Icons.Outlined.AccountCircle, title = "Nombre y apellidos", content = selectedContact.name)
            InfoRow(icon = Icons.Outlined.MailOutline, title = "Email", content = selectedContact.email)
            InfoRow(icon = Icons.Outlined.Info, title = "Género", content = selectedContact.gender)
            InfoRow(icon = Icons.Outlined.DateRange, title = "Fecha de registro", content = selectedContact.registerDate)
            InfoRow(icon = Icons.Outlined.Phone, title = "Teléfono", content = selectedContact.phone)
            InfoMapRow(contactCity = selectedContact.city)
        }
    }
}