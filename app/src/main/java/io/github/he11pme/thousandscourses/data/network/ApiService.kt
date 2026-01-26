package io.github.he11pme.thousandscourses.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.he11pme.thousandscourses.data.model.CoursesResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private const val BASE_URL = "https://drive.usercontent.google.com/"

private val json = Json

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("u/0/uc")
    suspend fun getCourses(
        @Query("id") id: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") export: String = "download"
    ): CoursesResponse
}

@ActivityRetainedScoped
class Api @Inject constructor() {
    val retrofitService: ApiService by lazy {
        retrofit.newBuilder()
            .build()
            .create(ApiService::class.java)
    }
}