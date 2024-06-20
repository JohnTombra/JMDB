package com.smartapps.jmdb.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novaapps.jmdb.data.model.LoginBody
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    val repository: JmdbRepository
): ViewModel() {




    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState



    fun onEvent(event: LoginUiEvent){

        when(event){
            LoginUiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }
            is LoginUiEvent.UpdateEmail -> {
                Log.d("JMDBBBX","E ${event.value}")
                _uiState.update { it.copy(email = event.value) }
            }
            is LoginUiEvent.UpdatePassword -> {
                Log.d("JMDBBBX","P ${event.value}")
                _uiState.update { it.copy(password = event.value) }
            }

            LoginUiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
            LoginUiEvent.Proceed -> {

                if(_uiState.value.email.isEmpty()){
                    Log.d("JMDBBBX","Enter email")
                    _uiState.update { it.copy(error = "Enter your email") }
                    return
                }

                if(_uiState.value.password.isEmpty()){
                    Log.d("JMDBBBX","Enter password")
                    _uiState.update { it.copy(error = "Enter your password") }
                    return
                }
                Log.d("JMDBBBX","loading")
                _uiState.update { it.copy(loading = true) }

//                val loginBody = LoginBody(
//                    username  = _uiState.value.email,
//                    password = _uiState.value.password
//                )
//
//
//                    repository.login(loginBody,{
//                        Log.d("JMDBBBX","success")
//                        _uiState.update { it.copy(navigate = true) }
//                    }){error ->
//                        Log.d("JMDBBBX","error")
//                        _uiState.update { it.copy(loading = false, error = error) }
//                    }



            }
            LoginUiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }
        }

    }




}