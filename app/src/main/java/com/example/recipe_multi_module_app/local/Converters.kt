package com.example.recipe_multi_module_app.local
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromIngredientsList(value: List<Pair<String, String>?>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<String, String>?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<Pair<String, String>?>? {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<String, String>?>>() {}.type
        return gson.fromJson(value, type)
    }
}
