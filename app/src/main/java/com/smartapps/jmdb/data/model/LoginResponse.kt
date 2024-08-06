package com.novaapps.jmdb.data.model

import com.smartapps.jmdb.enumeration.data.model.jmdb.Data


data class LoginResponse(
    val `data`: Data,
    val jwt: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)