package com.smartapps.jmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LandUse(
    @PrimaryKey val id: Int,
    val land_use: String,
    val land_use_code: String
)
