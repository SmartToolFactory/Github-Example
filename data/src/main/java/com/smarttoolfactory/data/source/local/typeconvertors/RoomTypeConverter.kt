package com.smarttoolfactory.data.source.local.typeconvertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smarttoolfactory.data.model.local.OwnerEntity


/**
 * Converter is required to insert  [OwnerEntity] to database and convert back to specified
 * class
 */
class RoomTypeConverter {

    @TypeConverter
    fun fromOwner(owner: OwnerEntity): String {
        return Gson().toJson(owner)
    }


    @TypeConverter
    fun fromString(value: String): OwnerEntity {
        val mapType = object : TypeToken<OwnerEntity>() {}.type
        return Gson().fromJson(value, mapType)
    }


}