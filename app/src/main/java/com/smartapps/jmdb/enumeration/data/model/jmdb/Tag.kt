package com.smartapps.jmdb.enumeration.data.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Tag(
    val status: Boolean,
    @PrimaryKey val tag: String,
    val message: String
)