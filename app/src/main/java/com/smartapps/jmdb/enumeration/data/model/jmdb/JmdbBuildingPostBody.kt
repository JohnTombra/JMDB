package com.smartapps.jmdb.enumeration.data.model.jmdb

data class JmdbBuildingPostBody(
    val `data`: List<DataTagged>,
    val page: Int,
    val pageLength: String,
    val total: Int
)