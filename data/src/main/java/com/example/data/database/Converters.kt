package com.example.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun stringToList(str : String) : List<String>{
        val listType = object : TypeToken<List<String>>(){}.type
        val list = Gson().fromJson<List<String>>(str,listType) ?: emptyList()
        return list
    }

    @TypeConverter
    fun listToString(list : List<String>) : String{
        return Gson().toJson(list)
    }
}