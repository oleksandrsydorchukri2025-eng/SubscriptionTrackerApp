package com.example.subscriptiontracker.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("subscriptions")
    suspend fun addSubscription(
        @Header("Authorization") token: String,
        @Body request: AddSubscriptionRequest
    ): Response<AddSubscriptionResponse>

    @GET("subscriptions")
    suspend fun getSubscriptions(
        @Header("Authorization") token: String
    ): Response<List<Subscription>>

    @DELETE("subscriptions/{id}")
    suspend fun deleteSubscription(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void> // Повертає порожню відповідь, якщо видалення успішне
}
