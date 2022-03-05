package com.example.catapp.data.repository.datasourceimpl

import com.example.catapp.data.db.CatBreedDAO
import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.data.repository.datasource.CatLocalDataSource

class CatLocalDataSourceImpl(private val catBreedDAO: CatBreedDAO) : CatLocalDataSource {
    override suspend fun saveCatBreedToDB(catBreed: CatBreed) {
        catBreedDAO.insert(catBreed)
    }
}