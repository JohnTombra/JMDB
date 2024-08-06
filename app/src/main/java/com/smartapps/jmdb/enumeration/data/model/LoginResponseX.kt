package com.smartapps.jmdb.enumeration.data.model



data class LoginResponseX(
    val `data`: com.smartapps.jmdb.enumeration.data.model.DataL,
    val jwt: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: com.smartapps.jmdb.enumeration.data.model.User
)