package com.example.catapp.data.model.catbreeds


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "catBreeds"
)
data class CatBreed(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("temperament")
    val temperament: String,
) : Serializable