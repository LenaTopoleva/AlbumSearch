package com.lenatopoleva.albumsearch.model.datasource.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSource
import com.lenatopoleva.albumsearch.utils.mapToMediaList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit: IDataSource {

    override suspend fun getAlbumsByTitle(title: String): List<Media>? {
        return getService(BaseInterceptor.interceptor).getAlbumsByTitle(albumTitle = title).await().mapToMediaList()
    }

    override suspend fun getAlbumDetailsById(id: Int): List<Media>? {
        return getService(BaseInterceptor.interceptor).getAlbumDetailsById(albumId = id).await().mapToMediaList()
    }

    private fun getService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    private fun gson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()



    companion object {
        private const val BASE_URL_LOCATIONS = "https://itunes.apple.com/"
    }

}