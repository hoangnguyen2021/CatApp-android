package com.example.catapp.data.repository.datasource

import com.example.catapp.data.model.catbreeds.CatBreed

interface CatLocalDataSource {
    suspend fun saveCatBreedToDB(catBreed: CatBreed)
}