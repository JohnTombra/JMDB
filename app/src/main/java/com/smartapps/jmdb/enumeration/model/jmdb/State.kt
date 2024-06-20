package com.smartapps.jmdb.enumeration.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class State(
    val country_id: Int,
    val state: String,
    val state_code: String,
    @PrimaryKey val state_id: Int
)