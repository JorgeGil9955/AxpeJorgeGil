package es.jgalcolea.axpejorgegil.webService

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactService {

    @GET("api/")
    suspend fun getContacts(@Query("page") page: Int): Response<JsonObject>

}