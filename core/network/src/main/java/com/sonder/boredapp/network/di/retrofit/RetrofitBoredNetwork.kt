package com.sonder.boredapp.network.di.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sonder.boredapp.core.network.BuildConfig
import com.sonder.boredapp.network.di.BoredNetworkDataSource
import com.sonder.boredapp.network.di.model.ActivityResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitBoredNetworkApi {
    @GET(value = "activity")
    suspend fun getActivity(
        @Query("type") type: String? = null,
    ): ActivityResponse
}

private const val BoredApiBaseUrl = BuildConfig.BACKEND_URL

@Singleton
class RetrofitBoredNetwork @Inject constructor(
    networkJson: Json
) : BoredNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BoredApiBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                ).build()
        ).addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitBoredNetworkApi::class.java)

    override suspend fun getActivity(type: String?): ActivityResponse = networkApi.getActivity(type)
}
