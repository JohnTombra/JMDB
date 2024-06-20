package com.smartapps.jmdb.ui.screens.form3

import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.form2.Form2UiEvent

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