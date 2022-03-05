package com.example.catapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.catapp.data.model.catbreeds.CatBreed

@Database(
    entities = [CatBreed::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CatBreedDatabase : RoomDatabase() {
    abstract fun getCatBreedDAO(): CatBreedDAO
}