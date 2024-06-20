package com.smartapps.jmdb.enumeration.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BuildingType(
    @PrimaryKey val idbuilding_types: Int,
    val building_type: String
)
