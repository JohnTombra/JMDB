package com.smartapps.jmdb.enumeration.data.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Area(
    @PrimaryKey val id: Int,
    val area: String,
    val zone: String,
    val lga_id: Int
)
