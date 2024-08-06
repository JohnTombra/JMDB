package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.Area
import kotlinx.coroutines.flow.Flow


@Dao
interface JmdbAreaDao {



    @Query("SELECT * from area")
    fun getAll(): List<Area>


    @Query("SELECT * FROM area WHERE id LIKE :id")
    fun getById(id: String): Area

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(area: Area)



    @Delete
    fun delete(area: Area)





}