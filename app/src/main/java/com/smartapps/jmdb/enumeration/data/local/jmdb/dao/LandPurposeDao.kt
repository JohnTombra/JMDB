package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.data.model.LandPurpose




@Dao
interface LandPurposeDao {



    @Query("SELECT * from LandPurpose")
    fun getAll(): List<LandPurpose>


    @Query("SELECT * FROM landpurpose WHERE id LIKE :id")
    fun getById(id: String): LandPurpose

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(landPurpose: LandPurpose)


    @Delete
    fun delete(landPurpose: LandPurpose)





}