package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.data.model.LandPurpose
import com.smartapps.jmdb.data.model.LandUse




@Dao
interface LandUseDao {



    @Query("SELECT * from LandUse")
    fun getAll(): List<LandUse>


    @Query("SELECT * FROM landuse WHERE id LIKE :id")
    fun getById(id: String): LandUse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(landUse: LandUse)


    @Delete
    fun delete(landUse: LandUse)





}