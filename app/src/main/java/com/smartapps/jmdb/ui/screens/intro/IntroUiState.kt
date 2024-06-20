package com.smartapps.jmdb.ui.screens.intro




data class IntroUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val tin: String ="",
    val registrationType: String = "New registration",
    val navigate: Boolean = false
)
