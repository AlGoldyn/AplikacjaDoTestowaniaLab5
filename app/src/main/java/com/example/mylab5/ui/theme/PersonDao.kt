package com.example.mylab5.ui.theme

import androidx.room.*

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY id ASC")
    suspend fun getAll(): List<Person>

    @Insert
    suspend fun insert(person: Person)

    @Query("DELETE FROM Person WHERE id = :id")
    suspend fun deleteById(id: Int)
}

