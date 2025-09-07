package com.example.network

import androidx.room.Database

@Database(entities = [String::class], version = 1, exportSchema = false)
abstract class DatabaseString: RoomDatabase(){
    abstract fun userDao() : UserDao
}