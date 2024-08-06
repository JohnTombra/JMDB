package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.Time


@Dao
interface TimeDao {



    @Query("SELECT * from time")
    fun getAll(): List<Time>


    @Query("SELECT * FROM time WHERE timeId LIKE :id")
    fun getById(id: String): Time

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(time: Time)

    @Delete
    fun delete(time: Time)





}