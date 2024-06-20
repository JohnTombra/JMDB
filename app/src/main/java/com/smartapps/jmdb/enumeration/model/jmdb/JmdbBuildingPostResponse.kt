package com.smartapps.jmdb.enumeration.model.jmdb

data class JmdbBuildingPostResponse(
    val status: String,
    val message: String,
    val nextpage: Int,
    val isEnd: Int,
    val syncIds: List<String>
)
