package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.model.jmdb.BuildingType

import kotlinx.coroutines.flow.Flow


@Dao
interface BuildingTypeDao {



    @Query("SELECT * from buildingType")
    fun getAll(): List<BuildingType>


    @Query("SELECT * FROM buildingtype WHERE idbuilding_types LIKE :id")
    fun getById(id: String): BuildingType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(buildingType: BuildingType)



    @Delete
    fun delete(buildingType: BuildingType)





}