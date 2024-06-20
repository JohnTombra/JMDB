package com.smartapps.jmdb.ui.screens.form2

import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent

sealed class Form2UiEvent {

    data object ClearError: Form2UiEvent()
    data object ClearNavigation: Form2UiEvent()
    data object Proceed: Form2UiEvent()
    data object UpdateLoading: Form2UiEvent()


    data class UpdatePLNo(val value: String): Form2UiEvent()

    data class UpdateDistrict(val value: String): Form2UiEvent()

    data class UpdateCountry(val value: String): Form2UiEvent()

    data class UpdateState(val value: String): Form2UiEvent()

    data class UpdatePOBox(val value: String): Form2UiEvent()

    data class UpdateLGA(val value: String): Form2UiEvent()

    data class UpdateCO(val value: String): Form2UiEvent()

    data class UpdateAdditionalInformation(val value: String): Form2UiEvent()

    data class UpdateStreetName(val value: String): Form2UiEvent()

}