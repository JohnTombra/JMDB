package com.smartapps.jmdb.registration.ui.screens.form4



sealed class Form4UiEvent {

    data object ClearError: Form4UiEvent()
    data object ClearNavigation: Form4UiEvent()
    data object Proceed: Form4UiEvent()
    data object UpdateLoading: Form4UiEvent()

    data class UpdatePLNo(val value: String): Form4UiEvent()

    data class UpdateDistrict(val value: String): Form4UiEvent()

    data class UpdateCountry(val value: String): Form4UiEvent()

    data class UpdateState(val value: String): Form4UiEvent()

    data class UpdatePOBox(val value: String): Form4UiEvent()

    data class UpdateLGA(val value: String): Form4UiEvent()

    data class UpdateCO(val value: String): Form4UiEvent()

    data class UpdateAdditionalInformation(val value: String): Form4UiEvent()

    data class UpdateStreetName(val value: String): Form4UiEvent()
}