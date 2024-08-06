package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategory




@Dao
interface BuildingCategoryDao {



    @Query("SELECT * from buildingCategory")
    fun getAll(): List<BuildingCategory>


    @Query("SELECT * FROM buildingcategory WHERE idbuilding_category LIKE :id")
    fun getById(id: String): BuildingCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(buildingCategory: BuildingCategory)



    @Delete
    fun delete(buildingCategory: BuildingCategory)





}