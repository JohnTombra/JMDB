package com.smartapps.jmdb.enumeration.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AssessmentItem(
    val assessment_item_id: Int,
   @PrimaryKey val item_ref: Int,
    val assessment_group: String,
    val assessment_item_name: String,
    val tax_base_amount: String,
    val asset_type: String
)
