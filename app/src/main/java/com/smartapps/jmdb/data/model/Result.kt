package com.smartapps.jmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Result(
    val assessment_group: String,
    @PrimaryKey val assessment_item_id: Int,
    val assessment_item_name: String,
    val asset_type: String,
    val item_ref: Int,
    val tax_base_amount: String
)