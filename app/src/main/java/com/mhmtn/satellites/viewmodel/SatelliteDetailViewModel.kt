package com.mhmtn.satellites.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.mhmtn.satellites.model.Posses
import com.mhmtn.satellites.model.SatelliteDetail
import com.mhmtn.satellites.repo.SatellitesRepo
import com.mhmtn.satellites.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val repo : SatellitesRepo,
    application: Application
) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    suspend fun getSatelliteDetail(id: Int): Resource<SatelliteDetail?> {
        return repo.getSatelliteDetail(context = context, id = id)
    }

    suspend fun getSatellitePositions(id: Int): Resource<Posses?> {
        return repo.getPositions(context, id)
    }

}