package com.example.catapp.data.model.catbreeds


import com.google.gson.annotations.SerializedName

data class Image(
    /*@SerializedName("id")
    val id: String,*/
    @SerializedName("url")
    val url: String,
)