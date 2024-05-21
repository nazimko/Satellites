package com.mhmtn.satellites.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.repo.SatellitesRepo
import com.mhmtn.satellites.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val repo: SatellitesRepo,
    application: Application
) : AndroidViewModel(application = application) {

    var satellitesList = mutableStateOf<List<Satellites>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    init {
        loadSatellites()
    }

    fun loadSatellites(){
        viewModelScope.launch {
            isLoading.value = true
            val list = repo.getSatelliteList(context)

            when(list){
                is Resource.Success -> {
                    val sats = list.data!!.mapIndexed { index, satellites ->
                        Satellites(satellites.id,satellites.active,satellites.name)
                    }
                    isLoading.value = false
                    satellitesList.value += sats
                    errorMessage.value = ""
                }

                is Resource.Error -> {
                    errorMessage.value = list.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    errorMessage.value = ""
                    isLoading.value = true
                }
            }
        }
    }
}