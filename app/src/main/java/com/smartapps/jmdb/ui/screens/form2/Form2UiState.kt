package com.smartapps.jmdb.ui.screens.form2

import com.smartapps.jmdb.enumeration.model.jmdb.Lga


data class Form2UiState(
    val lgas: List<Lga> = listOf(),
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,



    val plNo: String = "",
    val district: String = "",
    val country: String = "",
    val state: String = "",
    val poBox: String = "",
    val lga: String = "",
    val co: String = "",
    val streetName: String = "",
    val additionalInformation: String = "",

//    val firstName: String = "",
//    val phone1: String  = "",
//    val phone2: String  = "",
//    val middleName: String = "",
//    val surname: String = "",
//    val email: String = "",
//    val identification: String = "",
//    val idNumber: String = ""

)
