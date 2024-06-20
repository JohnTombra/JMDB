package com.smartapps.jmdb.ui.screens.login

import com.smartapps.jmdb.ui.screens.form5.Form5UiEvent

sealed class LoginUiEvent {

    data object ClearError: LoginUiEvent()

    data object UpdateLoading: LoginUiEvent()

    data object Proceed: LoginUiEvent()

    data object ClearNavigation: LoginUiEvent()


    data class UpdateEmail(val value: String): LoginUiEvent()

    data class UpdatePassword(val value: String): LoginUiEvent()


}