package com.smartapps.jmdb.ui.screens.intro

import com.smartapps.jmdb.ui.screens.form5.Form5UiEvent

sealed class IntroUiEvent {

    data class UpdateTin(val value: String): IntroUiEvent()

    data class UpdateType(val value: String): IntroUiEvent()

    data object Proceed: IntroUiEvent()

    data object ClearError: IntroUiEvent()

    data object ClearNavigation: IntroUiEvent()

    data object UpdateLoading: IntroUiEvent()

}