package com.example.catapp.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.data.util.Resource
import com.example.catapp.domain.usecase.GetCatBreedUseCase
import com.example.catapp.domain.usecase.GetCatBreedsUseCase
import com.example.catapp.domain.usecase.SaveCatBreedUseCase
import kotlinx.coroutines.launch


class CatBreedsViewModel(
    private val app: Application,
    private val getCatBreedsUseCase: GetCatBreedsUseCase,
    private val getCatBreedUseCase: GetCatBreedUseCase,
    private val saveCatBreedUseCase: SaveCatBreedUseCase
) : AndroidViewModel(app) {
    private val _catBreeds = MutableLiveData<Resource<CatBreeds>>()
    val catBreeds: LiveData<Resource<CatBreeds>>
        get() = _catBreeds
    private val _catBreed = MutableLiveData<Resource<CatBreedDetails>>()
    val catBreed: LiveData<Resource<CatBreedDetails>>
        get() = _catBreed

    fun getCatBreeds() {
        viewModelScope.launch {
            _catBreeds.value = Resource.Loading()
            try {
                if (isNetworkAvailable(app)) {
                    // execute() is a suspend function
                    val apiResult = getCatBreedsUseCase.execute()
                    _catBreeds.setValue(apiResult)
                } else {
                    _catBreeds.setValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                _catBreeds.setValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getCatBreed(breedId: String) {
        viewModelScope.launch {
            _catBreed.value = Resource.Loading()
            try {
                if (isNetworkAvailable(app)) {
                    // execute() is a suspend function
                    val apiResult = getCatBreedUseCase.execute(breedId)
                    _catBreed.setValue(apiResult)
                } else {
                    _catBreed.setValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                _catBreed.setValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun saveCatBreed(catBreed: CatBreed) {
        viewModelScope.launch {
            saveCatBreedUseCase.execute(catBreed)
        }
    }

    /**
     *  Checks if Internet is available.
     *
     *  @param context the context
     *  @return true if the Internet is available.
     */
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}