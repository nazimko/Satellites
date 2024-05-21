package com.mhmtn.satellites.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.util.Resource

class API  {

    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseJsonToModel(jsonString: String): List<Satellites> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<Satellites>>() {}.type)
    }

}