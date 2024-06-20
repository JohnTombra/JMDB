package com.smartapps.jmdb.ui.screens.form4

import com.smartapps.jmdb.enumeration.model.jmdb.Lga


data class Form4UiState(
    val lgas: List<Lga> = listOf(),
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,


    val plNo: String = "",
    val district: String = "",
    val country: String = "",
    val state: String = "",
    val streetName: String = "",
    val poBox: String = "",
    val lga: String = "",
    val co: String = "",
    val additionalInformation: String = "",


    )
