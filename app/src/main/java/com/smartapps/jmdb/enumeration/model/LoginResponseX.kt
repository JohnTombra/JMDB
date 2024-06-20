package com.smartapps.jmdb.enumeration.model



data class LoginResponseX(
    val `data`: DataL,
    val jwt: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)