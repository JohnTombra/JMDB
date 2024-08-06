package com.smartapps.jmdb.registration.ui.screens.form5


sealed class Form5UiEvent {

    data object ClearError: Form5UiEvent()
    data object ClearNavigation: Form5UiEvent()
    data object Proceed: Form5UiEvent()
    data object UpdateLoading: Form5UiEvent()


    data class UpdateLandUse(val value: String): Form5UiEvent()
    data class UpdateDistrict(val value: String): Form5UiEvent()
    data class UpdateDescription(val value: String): Form5UiEvent()
    data class UpdatePurpose(val value: String): Form5UiEvent()
    data class UpdateLga(val value: String): Form5UiEvent()

}