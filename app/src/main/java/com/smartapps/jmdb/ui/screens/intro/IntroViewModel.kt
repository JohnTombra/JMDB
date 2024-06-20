package com.smartapps.jmdb.ui.screens.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.navigation.Routes
import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IntroViewModel(
    val repository: JmdbRepository
): ViewModel() {



    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState



    fun onEvent(event: IntroUiEvent){

        when(event){
            IntroUiEvent.Proceed -> {

                if(_uiState.value.tin.isEmpty()){
                    Log.d("JMDBBBX","Enter tin")
                    _uiState.update { it.copy(error = "Enter your tin") }
                    return
                }

                if(_uiState.value.registrationType.isEmpty()){
                    Log.d("JMDBBBX","Enter type")
                    _uiState.update { it.copy(error = "Select registration type") }
                    return
                }
                Log.d("JMDBBBX","loading ${_uiState.value.tin}")

                _uiState.update { it.copy(loading = true) }

//                repository.verifyTin(_uiState.value.tin,{
//                    Log.d("JMDBBBX","success")
//                    _uiState.update { it.copy(navigate = true) }
//                }){error->
//                    Log.d("JMDBBBX","error")
//                    _uiState.update { it.copy(error = error, loading = false) }
//                }
            }
            is IntroUiEvent.UpdateTin -> {
                Log.d("JMDBBBX","T ${event.value}")
                _uiState.update { it.copy(tin = event.value) }
            }
            is IntroUiEvent.UpdateType -> {
                Log.d("JMDBBBX","R ${event.value}")
                _uiState.update { it.copy(registrationType = event.value) }
            }

            IntroUiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }

            IntroUiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }

            IntroUiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }
        }

    }


}