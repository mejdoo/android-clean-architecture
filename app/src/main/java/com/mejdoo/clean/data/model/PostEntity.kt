package com.mejdoo.clean.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "post")
data class PostEntity(

        @SerializedName("userId")
        val userId: Int,

        @PrimaryKey
        @SerializedName("id")
        val id: Int,

        @SerializedName("title")
        val title: String,

        @SerializedName("body")
        val body: String

)