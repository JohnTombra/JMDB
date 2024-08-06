package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga

import kotlinx.coroutines.flow.Flow


@Dao
interface JmdbLgaDao {



    @Query("SELECT * from lga")
    fun getAll(): List<Lga>


    @Query("SELECT * FROM lga WHERE lga_id LIKE :id")
    fun getById(id: String): Lga

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lga: Lga)



    @Delete
    fun delete(lga: Lga)





}