package com.example.catapp.data.repository

import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.data.repository.datasource.CatLocalDataSource
import com.example.catapp.data.repository.datasource.CatRemoteDataSource
import com.example.catapp.data.util.Resource
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CatRepositoryImpl(
    private val catRemoteDataSource: CatRemoteDataSource,
    private val catLocalDataSource: CatLocalDataSource
) : CatRepository {
    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getCatBreeds(): Resource<CatBreeds> {
        return withContext(Dispatchers.IO) {
            responseToResource(catRemoteDataSource.getCatBreeds())
        }
    }

    override suspend fun getCatBreed(breedId: String): Resource<CatBreedDetails> {
        return withContext(Dispatchers.IO) {
            responseToResource(catRemoteDataSource.getCatBreed(breedId))
        }
    }

    override suspend fun saveCatBreed(catBreed: CatBreed) {
        return catLocalDataSource.saveCatBreedToDB(catBreed)
    }
}