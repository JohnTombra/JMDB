package com.smartapps.jmdb.enumeration.data.model.jmdb

data class StreetsResponse(//
    val status: Boolean,
    val data: List<Street>,
    val message: String
)
