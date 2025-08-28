package com.example.network

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    companion object{
        private const val DB_NAME = "my_db_name"
        private const val DB_VERSION = 1

        private const val KEY_COUNT = "Key_COUNT"
        private const val PREFS_NAME = "Prefs_NAME"

    }

    private val dbHelper by lazy {DbHelper(this)}

    private var initCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            Log.i("InitCount", savedInstanceState.getInt(KEY_COUNT).toString())
            if (savedInstanceState.getInt(KEY_COUNT) == 3){
                findViewById<TextView>(R.id.textHello).text = "Third Initialization"
                initCount = 0
            }
        }
        else{
            Log.i("ER","Doesn't work")
        }

        Thread{
            try {
                saveDataToDB(HttpConnectionNetwork.test())
            } catch (e:Exception){
                println("ERROR - ${e.message}")
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        initCount++
        outState.putInt(KEY_COUNT,initCount)
        Log.i("Save","SavedState")
    }

    private fun getDataFromDB(context: Context): String{
        var count = 0
        val name = "null"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DbHelper.TABLE_NAME} WHERE $_ID = ?", arrayOf("1") )
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        if (cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex(_ID))
            val name = cursor.getString(1)
            count = cursor.getString(2)
        }

        cursor.close()
        db.close()

        return name
    }

    private fun saveDataToDB(value: String){

        val values = ContentValues()
        values.put(_ID,1)
        values.put(DbHelper.COLUMN_NAME, "count_x")

        dbHelper.writableDatabase
            .insertWithOnConflict(DbHelper.TABLE_NAME, null, values, CONFLICT_REPLACE)
    }

    private fun getCounterData(context: Context):Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        return prefs.getInt(KEY_COUNT, 0)
    }

    private fun saveCounterData(context: Context, value:Int ){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        prefs.edit()
            .putInt(KEY_COUNT,value)
            .apply()
    }

}