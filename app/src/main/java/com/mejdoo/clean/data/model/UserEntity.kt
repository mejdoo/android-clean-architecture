package com.mejdoo.clean.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "user")
data class UserEntity(

        @PrimaryKey
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("email")
        val email: String,


        @SerializedName("phone")
        val phone: String,

        @SerializedName("website")
        val website: String

)