package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.data.model.IdentityType




@Dao
interface IdentityTypeDao {



    @Query("SELECT * from identitytype")
    fun getAll(): List<IdentityType>


    @Query("SELECT * FROM identitytype WHERE id LIKE :id")
    fun getById(id: String): IdentityType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(identityType: IdentityType)



    @Delete
    fun delete(identityType: IdentityType)





}