package com.smartapps.jmdb.enumeration.data.model.jmdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity() //full details about the application..
data class Data( //lga,area,street
    val apartment_type: Int, //...
    val approval_code: String,
    val building_category_id: Int, //...x
    @PrimaryKey val building_id: String,
    val building_image: String,
    val building_name: String,
    val building_number: String,
    val id: Int,
    val latitude: String,
    val lga: Int,
    val longitude: String,
    val no_of_apartments: String,
    val owner_email: String,
    val owner_mobile_no: String,
    val owner_name: String,
    val state_id: Int,
    val status: Int,
    val street_id: Int,
    val taxitem: List<Int>, //tax items
    val tin: String,
    val ward: Int,
    val line: String,
)