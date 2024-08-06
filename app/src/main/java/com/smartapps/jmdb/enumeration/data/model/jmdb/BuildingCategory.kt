package com.smartapps.jmdb.enumeration.data.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BuildingCategory(
    @PrimaryKey val idbuilding_category: Int,
    val building_category: String
)
