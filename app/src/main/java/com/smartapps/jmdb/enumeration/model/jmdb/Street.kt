package com.smartapps.jmdb.enumeration.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Street(
    @PrimaryKey val idstreet: Int,//the real id
    val street: String,
    val city_id: String,
    val street_id: Int,
    val area_id: String
)
