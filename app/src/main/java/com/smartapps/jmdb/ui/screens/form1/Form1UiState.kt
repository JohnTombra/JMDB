package com.smartapps.jmdb.ui.screens.form1

import com.smartapps.jmdb.data.model.IdentityType
import com.smartapps.jmdb.enumeration.model.jmdb.Lga


data class Form1UiState(
    val lgas: List<Lga> = listOf(),
    val identificationTypes: List<IdentityType> = listOf(),
    val title: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val surname: String = "",
   val gender: String = "",
   val dob: String = "",
   val occupation: String = "",
   val nationality: String  = "",
   val state: String = "",
   val lga: String = "",
   val phone1: String = "",
   val phone2: String = "",
   val email: String  = "",
   val tin: String  = "",
   val identification: String  = "",
   val idNumber: String  = "",
   val applicationType: String = "",

    val loading: Boolean = false,
    val navigate: Boolean = false,
    val error: String? = null,
    val showOther: Boolean = false
)
