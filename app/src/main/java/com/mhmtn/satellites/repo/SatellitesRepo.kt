package com.mhmtn.satellites.repo

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.service.API
import com.mhmtn.satellites.util.Resource
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class SatellitesRepo @Inject constructor(private val api : API){


    fun getSatelliteList (context: Context) : Resource<List<Satellites>> {

       val response = api.readJsonFromAssets(context,"satellites.json")
       val satelliteList =  try {
           api.parseJsonToModel(response)
       } catch (e:Exception) {
           return Resource.Error("Error.")
       }

       return Resource.Success(satelliteList)

   }

}