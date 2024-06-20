package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.model.jmdb.Data

import kotlinx.coroutines.flow.Flow


@Dao
interface JmdbBuildingDao {



    @Query("SELECT * from data")
    fun getAll(): List<Data>


    @Query("SELECT * FROM data WHERE building_id LIKE :id")
    fun getById(id: String): Data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Data)



    @Delete
    fun delete(data: Data)





}