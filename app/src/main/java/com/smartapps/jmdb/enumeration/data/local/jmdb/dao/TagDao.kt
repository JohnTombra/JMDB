package com.smartapps.jmdb.enumeration.data.local.jmdb.dao

import androidx.room.*
import com.smartapps.jmdb.enumeration.data.model.jmdb.Tag
import com.smartapps.jmdb.enumeration.data.model.jmdb.Time


@Dao
interface TagDao {



    @Query("SELECT * from tag")
    fun getAll(): List<Tag>


    @Query("SELECT * FROM tag WHERE tag LIKE :id")
    fun getById(id: String): Tag

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag)

    @Delete
    fun delete(tag: Tag)





}