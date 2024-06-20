package com.smartapps.jmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LandPurpose(
    @PrimaryKey val id: Int,
    val purpose: String
)
