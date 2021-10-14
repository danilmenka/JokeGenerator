package com.expl.jokegenerator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "joke_table")
data class Joke (
    @ColumnInfo @SerializedName("created_at") var createdAt : String,
    @ColumnInfo @SerializedName("icon_url") var iconUrl : String,
    @ColumnInfo @SerializedName("id") var id : String,
    @ColumnInfo @SerializedName("updated_at") var updatedAt : String,
    @ColumnInfo @SerializedName("url") var url : String,
    @PrimaryKey @ColumnInfo @SerializedName("value") var value : String
):Serializable
