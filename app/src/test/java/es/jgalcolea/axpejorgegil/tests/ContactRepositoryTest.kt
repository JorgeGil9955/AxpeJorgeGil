package es.jgalcolea.axpejorgegil.tests

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import es.jgalcolea.axpejorgegil.mock.MockResponses
import es.jgalcolea.axpejorgegil.model.repository.ContactRepository
import es.jgalcolea.axpejorgegil.webService.ContactService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import java.io.StringReader

class ContactRepositoryTest {
    @Test
    fun doesLoadContacts() = runBlocking {
        val mockContactService = mock<ContactService>()
        val mockListResponse1: JsonObject = JsonParser.parseReader(
            JsonReader(
                StringReader(
                    MockResponses.mockListJson1)
            )
        ).asJsonObject
        val mockListResponse2: JsonObject = JsonParser.parseReader(
            JsonReader(
                StringReader(
                    MockResponses.mockListJson2)
            )
        ).asJsonObject
        `when`(mockContactService.getContacts(1)).thenReturn(Response.success(mockListResponse1))
        `when`(mockContactService.getContacts(2)).thenReturn(Response.success(mockListResponse2))

        val contactRepository = ContactRepository(mockContactService)

        val pagingConfig = PagingConfig(pageSize = 20)

        val pager = TestPager(pagingConfig,contactRepository)

        val result = pager.refresh() as PagingSource.LoadResult.Page
        assert(result.data.count() == 20)

        val result2 = pager.append() as PagingSource.LoadResult.Page
        assert(result2.data.count() == 20)
    }
}