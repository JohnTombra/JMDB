package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*


@Dao
interface RevenueItemDao {



    @Query("SELECT * from result")
    fun getAll(): List<com.smartapps.jmdb.data.model.Result>


    @Query("SELECT * FROM result WHERE assessment_item_id LIKE :id")
    fun getById(id: Int): com.smartapps.jmdb.data.model.Result


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: com.smartapps.jmdb.data.model.Result)



    @Delete
    fun delete(result: com.smartapps.jmdb.data.model.Result)





}