package com.smartapps.jmdb.registration.ui.screens.form4

import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga



data class Form4UiState(
    val lgas: List<Lga> = listOf(),
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,


    val plNo: String = "",
    val district: String = "",
    val country: String = "Nigeria",
    val state: String = "Plateau state",
    val streetName: String = "",
    val poBox: String = "",
    val lga: String = "",
    val co: String = "",
    val additionalInformation: String = "",


    )
