package com.mhmtn.satellites.repo

import android.content.Context
import com.mhmtn.satellites.model.SatelliteDetail
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.service.SatelliteAPI
import com.mhmtn.satellites.util.Resource
import com.mhmtn.satellites.util.readJsonFromAssets
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class SatellitesRepo @Inject constructor(private val api : SatelliteAPI){


    suspend fun getSatelliteList (context: Context) : Resource<List<Satellites>> {

       val response = readJsonFromAssets(context,"satellites.json")

       val satelliteList =  try {
           api.parseSatelliteJsonToModel(response)
       } catch (e:Exception) {
           return Resource.Error("Error.")
       }

       return Resource.Success(satelliteList)

   }

    suspend fun getSatelliteDetail(context: Context,id: Int) : Resource<SatelliteDetail?> {

        val response = readJsonFromAssets(context,"satellite_detail.json")

        val satelliteDetail =  try {
            api.parseSatelliteDetailJsonToModel(response)
        } catch (e:Exception) {
            return Resource.Error("Error.")
        }
        val x = getItemById(satelliteDetail, targetId = id)

        return Resource.Success(x)
    }

    private fun getItemById(dataList: List<SatelliteDetail>, targetId:Int) : SatelliteDetail? {
        return dataList.find {
            it.id==targetId
        }
    }

}