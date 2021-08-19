package com.example.clima.data.source.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.clima.data.source.room.entity.AuthEntity.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName


@Entity(tableName = TABLE_NAME)
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val incrementId:  Long = 0,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("temperature")
    var temperature: String? = null,
    @ColumnInfo(name = COLUMN_CITY)
    var city: String? = null

){
    companion object {
        const val TABLE_NAME = "forecast_log"
        const val COLUMN_ID = "id"
        const val COLUMN_CITY = "city"
    }
}