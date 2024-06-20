package com.smartapps.jmdb.enumeration.model


data class CountriesResponse(
    val `data`: List<DataX> = listOf(),
    val status: Boolean = false
)