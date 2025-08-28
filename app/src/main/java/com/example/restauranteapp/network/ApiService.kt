package com.example.restauranteapp.network

import com.example.restauranteapp.MenuItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("items")
    suspend fun getAllItems(): Response<List<MenuItem>>

    @POST("items")
    suspend fun createItem(@Body item: MenuItem): Response<MenuItem>

    @PUT("items/{id}")
    suspend fun updateItem(@Path("id") id: Long, @Body item: MenuItem): Response<MenuItem>

    @DELETE("items/{id}")
    suspend fun deleteItem(@Path("id") id: Long): Response<Void>
}