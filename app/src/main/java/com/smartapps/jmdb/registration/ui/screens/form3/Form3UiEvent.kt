package com.smartapps.jmdb.registration.ui.screens.form3



sealed class Form3UiEvent {

    data object ClearError: Form3UiEvent()
    data object Navigate: Form3UiEvent()
    data object ClearNavigation: Form3UiEvent()
    data object Proceed: Form3UiEvent()
    data object UpdateLoading: Form3UiEvent()


    data class UpdateFirstName(val value: String): Form3UiEvent()
    data class UpdatePhone1(val value: String): Form3UiEvent()
    data class UpdateMiddleName(val value: String): Form3UiEvent()
    data class UpdatePhone2(val value: String): Form3UiEvent()
    data class UpdateSurname(val value: String): Form3UiEvent()
    data class UpdateEmail(val value: String): Form3UiEvent()
    data class UpdateIdentification(val value: String): Form3UiEvent()
    data class UpdateIdNumber(val value: String): Form3UiEvent()

}