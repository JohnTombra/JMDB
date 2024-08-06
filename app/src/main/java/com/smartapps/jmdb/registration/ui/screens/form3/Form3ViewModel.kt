package com.smartapps.jmdb.registration.ui.screens.form3

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form3Body
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.util.Constants

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class Form3ViewModel(
    val repository: JmdbRepository
): ViewModel()  {


    private val _uiState = MutableStateFlow(Form3UiState())
    val uiState: StateFlow<Form3UiState> = _uiState


    init {
        Constants.SKIP = false






                repository.getIdentificationItems{
                    val i =it
                    _uiState.update { it.copy(identificationTypes = i)}
                }




    }

    fun onEvent(event: Form3UiEvent){

        when(event){
            Form3UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    CoroutineScope(Dispatchers.Main).launch {
                    _uiState.update { it.copy(error = null) } }
                }
            }

            Form3UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
            Form3UiEvent.Proceed -> {


                if(_uiState.value.firstName.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your first name") }
                    Log.d("JMDB","Enter your first name")
                    return
                }

//                if(_uiState.value.middleName.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter your middle name") }
//                    Log.d("JMDB","Enter your middle name")
//                    return
//                }

                if(_uiState.value.surname.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your surname") }
                    Log.d("JMDB","Enter your surname")
                    return
                }

                if(_uiState.value.phone1.isEmpty()){
                    _uiState.update { it.copy(error = "Enter  phone number 1") }
                    Log.d("JMDB","Enter phone number 1")
                    return
                }

//                if(_uiState.value.phone2.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter phone number 2") }
//                    Log.d("JMDB","Enter phone number 2")
//                    return
//                }


                if(_uiState.value.email.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your email") }
                    Log.d("JMDB","Enter your email")
                    return
                }

                if(_uiState.value.identification.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your identification") }
                    Log.d("JMDB","Enter your identification")
                    return
                }

                if(_uiState.value.idNumber.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your id number") }
                    Log.d("JMDB","Enter your id number")
                    return
                }


                Log.d("JMDB","form validated")
             //   _uiState.update { it.copy(loading = true) }



                val form3Body = Form3Body(
                    rfn = uiState.value.firstName,
                    rsn = uiState.value.surname,
                    rmn = uiState.value.middleName,
                    rphone1 = uiState.value.phone1,
                    rphone2 = uiState.value.phone2,
                    rphone3 = "",
                    remail = uiState.value.email,
                    track_no = Constants.TRACK_NO,
                    rid = _uiState.value.identification,
                    rid_number = _uiState.value.idNumber
                )


                Constants.SKIP = false
                JmdbRepository.FORM_3 = form3Body
                _uiState.update { it.copy(navigate = true, loading = false) }


//                repository.submitForm3(form3Body,{
//                    TRACK_NO = it.track_no
//                    if(_uiState.value.loading) {
//                        Log.d("JMDB", "success")
//                        _uiState.update { it.copy(navigate = true, loading = false) }
//                    }
//                }){error->
//                    if(_uiState.value.loading) {
//                    Log.d("JMDB","error $error")
//                    _uiState.update { it.copy(error = error, loading = false) } }
//                }

            }
            is Form3UiEvent.UpdateEmail -> {
                _uiState.update { it.copy(email = event.value) }
            }
            is Form3UiEvent.UpdateFirstName -> {
                _uiState.update { it.copy(firstName = event.value) }
            }
            is Form3UiEvent.UpdateIdNumber -> {
                _uiState.update { it.copy(idNumber = event.value) }
            }
            is Form3UiEvent.UpdateIdentification -> {
                _uiState.update { it.copy(identification = event.value) }
            }
            is Form3UiEvent.UpdateMiddleName -> {
                _uiState.update { it.copy(middleName = event.value) }
            }
            is Form3UiEvent.UpdatePhone1 -> {
                _uiState.update { it.copy(phone1 = event.value) }
            }

            is Form3UiEvent.UpdatePhone2 -> {
                _uiState.update { it.copy(phone2 = event.value) }
            }
            is Form3UiEvent.UpdateSurname -> {
                _uiState.update { it.copy(surname = event.value) }
            }
            Form3UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }

            Form3UiEvent.Navigate -> {
                Constants.SKIP = true
                _uiState.update { it.copy(navigate = true, loading = false) }
            }
        }

    }


}