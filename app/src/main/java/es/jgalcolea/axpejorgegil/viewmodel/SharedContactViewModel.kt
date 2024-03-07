package es.jgalcolea.axpejorgegil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jgalcolea.axpejorgegil.model.entities.Contact
import es.jgalcolea.axpejorgegil.model.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SharedContactViewModel @Inject constructor(private val contactRepository: ContactRepository): ViewModel() {
    val contact: Flow<PagingData<Contact>> = Pager(PagingConfig(pageSize = 20)) {
        contactRepository
    }.flow.cachedIn(viewModelScope)

    var selectedContact: Contact = Contact()
}