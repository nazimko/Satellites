package com.mhmtn.satellites.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhmtn.satellites.model.SatelliteDetail
import com.mhmtn.satellites.model.Satellites

class SatelliteAPI  {

    suspend fun parseSatelliteJsonToModel(jsonString: String): List<Satellites> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<Satellites>>() {}.type)
    }

    suspend fun parseSatelliteDetailJsonToModel(jsonString: String): List<SatelliteDetail> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<SatelliteDetail>>() {}.type)
    }


}