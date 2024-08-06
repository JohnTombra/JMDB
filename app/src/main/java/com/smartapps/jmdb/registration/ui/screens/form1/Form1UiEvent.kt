package com.smartapps.jmdb.registration.ui.screens.form1

sealed class Form1UiEvent {

    data object ClearError: Form1UiEvent()
    data object ClearNavigation: Form1UiEvent()
    data object Proceed: Form1UiEvent()
    data object ShowOther: Form1UiEvent()
    data object UpdateLoading: Form1UiEvent()



    data class UpdateYear(val value: String): Form1UiEvent()

    data class UpdateMonth(val value: String): Form1UiEvent()

    data class UpdateDay(val value: String): Form1UiEvent()

    data class UpdateGender(val value: String): Form1UiEvent()

    data class UpdateDob(val value: String): Form1UiEvent()

    data class UpdateOccupation(val value: String): Form1UiEvent()

    data class UpdateNationality(val index: Int,val value: String): Form1UiEvent()

    data class UpdateState(val index: Int,val value: String): Form1UiEvent()

    data class UpdateLga(val index: Int,val value: String): Form1UiEvent()

    data class UpdatePhone1(val value: String): Form1UiEvent()

    data class UpdatePhone2(val value: String): Form1UiEvent()

    data class UpdateEmail(val value: String): Form1UiEvent()

    data class UpdateTin(val value: String): Form1UiEvent()

    data class UpdateIdentification(val index: Int,val value: String): Form1UiEvent()

    data class UpdateIdNumber(val value: String): Form1UiEvent()

    data class UpdateApplicationType(val value: String): Form1UiEvent()

    data class UpdateTitle(val value: String): Form1UiEvent()


    data class UpdateFirstName(val value: String): Form1UiEvent()

    data class UpdateSurname(val value: String): Form1UiEvent()

    data class UpdateMiddleName(val value: String): Form1UiEvent()

}