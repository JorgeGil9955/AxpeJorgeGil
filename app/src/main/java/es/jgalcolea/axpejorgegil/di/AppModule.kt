package es.jgalcolea.axpejorgegil.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jgalcolea.axpejorgegil.util.Constants
import es.jgalcolea.axpejorgegil.webService.ContactService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        var client = OkHttpClient()
        client = client.newBuilder().addInterceptor(Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("seed", "9955")
                .addQueryParameter("results", "20")
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }).build()

        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.ENDPOINT)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideContactService(retrofit: Retrofit): ContactService {
        return retrofit.create(ContactService::class.java)
    }

}