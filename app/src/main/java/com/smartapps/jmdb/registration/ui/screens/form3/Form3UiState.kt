package com.smartapps.jmdb.registration.ui.screens.form3

import com.smartapps.jmdb.data.model.IdentityType


data class Form3UiState(
    val identificationTypes: List<IdentityType> = listOf(),
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,

    val firstName: String  = "",
    val phone1: String = "",
    val middleName: String = "",
    val phone2: String = "",
    val surname: String  = "",
    val email: String  = "",
    val identification: String = "",
    val idNumber: String = ""

    )
