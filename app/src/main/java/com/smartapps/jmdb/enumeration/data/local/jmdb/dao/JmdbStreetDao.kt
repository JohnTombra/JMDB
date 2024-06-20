package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.model.jmdb.Street


import kotlinx.coroutines.flow.Flow


@Dao
interface JmdbStreetDao {



    @Query("SELECT * from street")
    fun getAll(): List<Street>


    @Query("SELECT * FROM street WHERE idstreet LIKE :id")
    fun getById(id: String): Street

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(street: Street)


    @Delete
    fun delete(street: Street)





}