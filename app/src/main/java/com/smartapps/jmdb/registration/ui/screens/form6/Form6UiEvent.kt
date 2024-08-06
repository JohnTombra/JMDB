package com.smartapps.jmdb.registration.ui.screens.form6


sealed class Form6UiEvent {

    data object ClearError: Form6UiEvent()
    data object ClearNavigation: Form6UiEvent()
    data object Proceed: Form6UiEvent()
    data object UpdateLoading: Form6UiEvent()

    data class UpdateBase64(val value: String): Form6UiEvent()


}