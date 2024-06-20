package com.smartapps.jmdb.enumeration.registrationdata.model



data class LoginResponse(
    val `data`: Data2,
    val jwt: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)