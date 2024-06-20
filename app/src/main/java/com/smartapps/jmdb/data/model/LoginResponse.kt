package com.novaapps.jmdb.data.model



data class LoginResponse(
    val `data`: Data,
    val jwt: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)