package com.example.catapp.ui.di

import android.app.Application
import androidx.room.Room
import com.example.catapp.data.db.CatBreedDAO
import com.example.catapp.data.db.CatBreedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideCatBreedDatabase(app: Application): CatBreedDatabase {
        return Room.databaseBuilder(app, CatBreedDatabase::class.java, "cat_breeds_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCatBreedDAO(catBreedDatabase: CatBreedDatabase): CatBreedDAO {
        return catBreedDatabase.getCatBreedDAO()
    }
}