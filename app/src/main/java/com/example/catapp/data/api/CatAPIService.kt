package com.example.catapp.data.api

import com.example.catapp.BuildConfig
import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatAPIService {
    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("v1/breeds")
    suspend fun getCatBreeds(): Response<CatBreeds>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("v1/images/search")
    suspend fun getCatBreed(
        @Query("breed_ids") breedId: String
    ): Response<CatBreedDetails>
}