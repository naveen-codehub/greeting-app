package com.example.myapplication.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class TodoDto(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("completed") val completed: Boolean
)


// In Java, an interface is a blueprint of a class that defines what a class must do, but not how it does it.
// API description
interface JsonPlaceholderApi {
    @GET("todos/{id}")
    suspend fun getTodo(
        @Path("id") id: Int
    ): TodoDto
}


// “When someone calls getTodo(2), send a GET request to
// BASE_URL + "todos/2" and convert the JSON response into TodoDto.”
object JsonPlaceholderService {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}

