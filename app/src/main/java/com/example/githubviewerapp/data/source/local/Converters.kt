package com.example.githubviewerapp.data.source.local

import androidx.room.TypeConverter
import com.example.githubviewerapp.domain.model.User
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromUserEntity(userEntity: User): String {
        return Gson().toJson(userEntity)
    }

    @TypeConverter
    fun toUserEntity(json: String): User {
        return Gson().fromJson(json, User::class.java)
    }
}
