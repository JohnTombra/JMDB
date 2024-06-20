package com.smartapps.jmdb.ui.screens.login




data class LoginUiState(
    val loading: Boolean = false,
    val navigate: Boolean = false,
    val email: String  = "",
    val password: String  = "",
    val error: String? = null
)
