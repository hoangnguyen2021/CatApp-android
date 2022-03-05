package com.example.catapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.catapp.data.model.catbreeds.CatBreed

@Dao
interface CatBreedDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catBreed: CatBreed)
}