package com.smartapps.jmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class IdentityType(
    @PrimaryKey val id: Int,
    val name: String,
)
