package com.example.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM entities WHERE name = :name")
    abstract suspend fun getMovieNames(name: String): List<MovieName>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun setMovieName(movieName: MovieName)
}