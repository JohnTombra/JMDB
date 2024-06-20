package com.smartapps.jmdb.enumeration.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lga(
    @PrimaryKey val lga_id: Int,
    val lga: String,
    val state_id: Int
)
