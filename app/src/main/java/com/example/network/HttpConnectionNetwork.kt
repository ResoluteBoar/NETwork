package com.example.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpConnectionNetwork {

    data class Movie(
        @SerializedName("name")
        val name: String,
        @SerializedName("rating")
        val rating: Long,
        @SerializedName("country")
        val country: String
    )

    public fun test():Movie{
        val urlString = "${BuildConfig.API_BASE_URL}/Top250Movies/${BuildConfig.API_KEY}"

        val urlObj = URL(urlString)
        val connection = urlObj.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode

        if(responseCode == HttpURLConnection.HTTP_OK){
            val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine = ""
            val response = StringBuffer()
            while (inputStream.readLine().also { inputLine = it }!= null){
                response.append(inputLine)

            }
            inputStream.close()
            println(response)

            val builder = GsonBuilder()
            val gson = builder.create()
            var movies = gson.fromJson(response, Movie::class.java);
            val json = gson.toJson(movies)
            movies.toString()
            Log.i("response",response.toString())
            return movies
        } else{
            println("ERROR -> $responseCode")
            return Movie("none",0, "none")
        }
    }
}