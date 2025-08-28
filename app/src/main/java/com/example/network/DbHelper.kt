package com.example.network

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class DbHelper(context: Context) : SQLiteOpenHelper(context, "Database", null, 1) {

    companion object{
        const val TABLE_NAME = "db1"
        const val COLUMN_NAME = "name"
        const val COLUMN_VALUE = "value"
    }

    private val CREATE_TABLE ="""CREATE TABLE IF NOT EXISTS $TABLE_NAME
        |($_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        |$COLUMN_NAME TEXT)""".trimMargin()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}