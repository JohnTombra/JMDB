package com.smartapps.jmdb.registration.ui.screens.form5

import com.smartapps.jmdb.data.model.LandPurpose
import com.smartapps.jmdb.data.model.LandUse
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga



data class Form5UiState(
    val skipState: Boolean = false,
    val lgas: List<Lga> = listOf(),
    val landPurposes: List<LandPurpose> = listOf(),
    val landUses: List<LandUse> = listOf(),
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,

    val landUse: String = "",
    val district: String = "",
    val description: String  = "",
    val purpose: String  = "",
    val lga: String = ""
    )
