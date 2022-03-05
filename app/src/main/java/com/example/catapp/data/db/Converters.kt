package com.example.catapp.data.db

import androidx.room.TypeConverter
import com.example.catapp.data.model.catbreeds.Image

class Converters {
    @TypeConverter
    fun fromImage(image: Image): String {
        return image.url
    }

    @TypeConverter
    fun toImage(url: String): Image {
        return Image(url)
    }
}