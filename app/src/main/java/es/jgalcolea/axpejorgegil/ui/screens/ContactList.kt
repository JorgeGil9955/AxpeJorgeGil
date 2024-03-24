package es.jgalcolea.axpejorgegil.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import es.jgalcolea.axpejorgegil.model.entities.Contact
import es.jgalcolea.axpejorgegil.ui.components.ContactRow
import es.jgalcolea.axpejorgegil.viewmodel.SharedContactViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactList(navController: NavController, viewModel: SharedContactViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val contactCombineFlow = if (isSearching) {
        combine(
            viewModel.contactFlow,
            flowOf(searchQuery)
        ) { pagingData, query ->
            pagingData.filter {
                it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true)
            }
        }
    } else {
        viewModel.contactFlow
    }
    val contactList: LazyPagingItems<Contact> = contactCombineFlow.collectAsLazyPagingItems()

    Scaffold (
        topBar = {
            if (!isSearching) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Contactos",
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { isSearching = true },
                            modifier = Modifier.testTag("searchButton")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Buscar",
                            )
                        }
                    }
                )
            } else {
                TopAppBar(
                    title = {
                        TextField(
                            value = searchQuery,
                            onValueChange = {searchQuery = it},
                            placeholder = { Text(text = "Buscar...") },
                            modifier = Modifier.testTag("searchInputField")
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                            searchQuery = ""
                            isSearching = false
                            },
                            modifier = Modifier.testTag("cancelSearchButton")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Cancelar"
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn (modifier = Modifier.padding(innerPadding).testTag("contactLazyColumn")) {
            items(
                count = contactList.itemCount
            ) { index ->
                val contact = contactList[index]
                contact?.let {
                    ContactRow(contact = it, onContactClick = {
                        viewModel.selectedContact = it
                        navController.navigate("ContactDetail")
                    })
                }
            }
        }
    }
}