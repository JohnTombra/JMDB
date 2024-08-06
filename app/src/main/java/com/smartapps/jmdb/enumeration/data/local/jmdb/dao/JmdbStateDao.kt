package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.State
import kotlinx.coroutines.flow.Flow


@Dao
interface JmdbStateDao {



    @Query("SELECT * from state")
    fun getAll(): List<State>


    @Query("SELECT * FROM state WHERE state_id LIKE :id")
    fun getById(id: String): State


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(state: State)


    @Delete
    fun delete(state: State)





}