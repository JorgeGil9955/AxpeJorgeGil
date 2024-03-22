package es.jgalcolea.axpejorgegil.di

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import es.jgalcolea.axpejorgegil.mock.MockResponses
import es.jgalcolea.axpejorgegil.webService.ContactService
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import retrofit2.Response
import java.io.StringReader
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
class TestModule {
    @Singleton
    @Provides
    fun provideContactService(): ContactService {
        //Mocks
        val mockContactService = Mockito.mock<ContactService>()
        val mockListResponse1: JsonObject = JsonParser.parseReader(JsonReader(StringReader(MockResponses.mockListJson1))).asJsonObject
        val mockListResponse2: JsonObject = JsonParser.parseReader(JsonReader(StringReader(MockResponses.mockListJson2))).asJsonObject

        val mockResponse1 = Response.success(200, mockListResponse1)
        val mockResponse2 = Response.success(200, mockListResponse2)
        runBlocking {
            Mockito.`when`(mockContactService.getContacts(1)).thenReturn(mockResponse1)
            Mockito.`when`(mockContactService.getContacts(2)).thenReturn(mockResponse2)
        }
        return mockContactService
    }
}