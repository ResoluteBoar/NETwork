package com.example.network

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import androidx.room.Database
import com.example.network.HttpConnectionNetwork.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class DbHelper : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: DbHelper? = null
    }
}