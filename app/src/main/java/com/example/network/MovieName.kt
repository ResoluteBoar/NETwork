package com.example.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "entities"
)
data class MovieName (
    @PrimaryKey(autoGenerate = true) val _id: Int,
    @ColumnInfo(name = "name") val name: String
)