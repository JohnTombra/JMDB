package com.smartapps.jmdb.enumeration.data.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class Time(
    @PrimaryKey val timeId: String,
)