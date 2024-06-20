package com.smartapps.jmdb.enumeration.model.jmdb

data class JmdbBuildingPostBody(
    val `data`: List<Data>,
    val page: Int,
    val pageLength: String,
    val total: Int
)