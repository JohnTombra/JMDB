package com.novaapps.jmdb.data.model


data class UserX(
    val agency_id: String = "",
    val allowdesktoplogin: Int,
    val allowmobilelogin: Int,
    val created_at: String,
    val department_id: String = "",
    val email: String,
    val first_use: Int,
    val firstname: String,
    val group_id: Int,
    val id: Int,
    val inactive: Int,
    val is_verified: Int,
    val middlename: String,
    val name: String,
    val password: String,
    val remember_token: String = "",
    val service_code: String,
    val service_id: String,
    val surname: String,
    val tax_office_id: Int,
    val updated_at: String,
    val user_code: String = "",
    val user_phone: String,
    val username: String
)