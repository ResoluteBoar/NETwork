package com.example.network

import android.content.Context
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Insert
import com.example.network.HttpConnectionNetwork.Movie

class MainActivity : AppCompatActivity() {


    companion object{
        private const val DB_NAME = "my_db_name"
        private const val DB_VERSION = 1

        private const val KEY_COUNT = "Key_COUNT"
        private  const val KEY_DB = "key_DB"
        private const val PREFS_INIT = "Prefs_INIT_COUNT"
        private const val PREFS_DB = "Prefs_INIT_DB"

    }

    private val movieDao by lazy {  }

    private var initCount = 1

    private var listOfMovie = HttpConnectionNetwork.MovieList("nameList", List<Movie>(250))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initCount++

        val savedCounter = getCounterData(this)
        listOfMovie = getDBFromCache(this)

        if (savedInstanceState != null) {
            Log.i("InitCount", savedCounter.toString())
            if (savedCounter == 3){
                setContentView(R.layout.third_init_intro)
                initCount = 0
            }
        }
        else{
            Log.i("ER","Doesn't work")
        }
        if (listOfMovie == null){
            Thread{
                try {
                    listOfMovie = HttpConnectionNetwork.test()
                    saveDataToDB(listOfMovie, "MovieList")
                } catch (e:Exception){
                    println("ERROR - ${e.message}")
                }
            }.start()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            searchByDB(this,listOfMovie)
        })
    }

    override fun onDestroy() {
        saveCache(this, initCount, listOfMovie)
        super.onDestroy()
    }

    private fun getDBFromCache(context: Context): HttpConnectionNetwork.MovieList {
        val prefsDB = context.getSharedPreferences(PREFS_DB, Context.MODE_PRIVATE)

        return prefsDB
    }

    private fun getDataFromDB(movieList: HttpConnectionNetwork.MovieList, place:Int):String{
        val movie = listOfMovie.list.get(place)

        return movie.name.toString()
    }

    private fun saveDataToDB(value: HttpConnectionNetwork.MovieList, key:String){

        @Insert
        fun addInfo(movieName: MovieName){
            listOfMovie
        }
    }

    private fun getCounterData(context: Context):Int {
        val prefs = context.getSharedPreferences(PREFS_INIT, Context.MODE_PRIVATE)

        return prefs.getInt(KEY_COUNT, 0)
    }

    private fun saveCache(context: Context, value:Int, dbInfo:HttpConnectionNetwork.MovieList ){
        val prefs = context.getSharedPreferences(PREFS_INIT, Context.MODE_PRIVATE)
        val prefsDB = context.getSharedPreferences(PREFS_DB, Context.MODE_PRIVATE)


        prefs.edit()
            .putInt(KEY_COUNT,value)
            .apply()
        prefsDB.edit()
            .putInt(KEY_DB, dbInfo)
            .apply()
    }

    private fun searchByDB(context: Context,movieList: HttpConnectionNetwork.MovieList){
        val editText = findViewById<EditText>(R.id.editTextText)
        val searchPlace: Int = editText.getText().toString().toInt()
        System.out.println(getDataFromDB(movieList, searchPlace))
    }

}