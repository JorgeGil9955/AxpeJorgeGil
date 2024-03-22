package es.jgalcolea.axpejorgegil.model.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.JsonObject
import es.jgalcolea.axpejorgegil.model.entities.Contact
import es.jgalcolea.axpejorgegil.util.DateUtil
import es.jgalcolea.axpejorgegil.webService.ContactService
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactService: ContactService): PagingSource<Int, Contact>() {

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        return try {
            val nextPage = params.key ?: 1
        val contactList = fetchContacts(nextPage)
        LoadResult.Page(
            data = contactList,
            prevKey = if (nextPage == 1 || contactList.isEmpty()) null else nextPage - 1,
            nextKey = if (contactList.isEmpty()) null else nextPage + 1
        )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    @Throws(Exception::class)
    private suspend fun fetchContacts(page: Int): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val contactResponse = contactService.getContacts(page)
        if (!contactResponse.isSuccessful ||
            contactResponse.body() == null ||
            contactResponse.body()!!.size() == 0) throw Exception("No se pudieron cargar los contactos")
        val jsonList = contactResponse.body()!!.getAsJsonArray("results")
        jsonList.forEach { contactObject ->
            contactList.add(parse(contactObject.asJsonObject))
        }
        return contactList
    }

    private fun parse(jsonObject: JsonObject): Contact {
        val nameWrapper = jsonObject.getAsJsonObject("name")
        val contactName = nameWrapper.get("first").asString + " " + nameWrapper.get("last").asString

        val contactEmail = jsonObject.get("email").asString

        val contactGender = when (jsonObject.get("gender").asString) {
            "male" -> "Hombre"
            "female" -> "Mujer"
            else -> "No binario"
        }

        val contactRegisterDate = DateUtil.parseDate(jsonObject.getAsJsonObject("registered").get("date").asString)

        val contactPhone = jsonObject.get("phone").asString

        val contactCity = jsonObject.getAsJsonObject("location").get("city").asString

        val contactPhoto = jsonObject.getAsJsonObject("picture").get("large").asString

        return Contact(
            name = contactName,
            email = contactEmail,
            gender = contactGender,
            registerDate = contactRegisterDate,
            phone = contactPhone,
            city = contactCity,
            photoUrl = contactPhoto
        )
    }
}