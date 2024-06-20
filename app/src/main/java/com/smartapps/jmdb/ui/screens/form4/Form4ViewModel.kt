package com.smartapps.jmdb.ui.screens.form4

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form4Body
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.form2.Form2UiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Form4ViewModel(
    val repository: JmdbRepository
): ViewModel()  {


    private val _uiState = MutableStateFlow(Form4UiState())
    val uiState: StateFlow<Form4UiState> = _uiState

    init {

        CoroutineScope(Dispatchers.IO).launch {


            val x = repository.getLgas()



            CoroutineScope(Dispatchers.Main).launch {

                _uiState.update { it.copy(lgas = x)


                }
            }
        }



    }

    fun onEvent(event: Form4UiEvent){

        when(event){
            Form4UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }

            Form4UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
            is Form4UiEvent.UpdateAdditionalInformation -> {
                _uiState.update { it.copy(additionalInformation = event.value) }
            }
            is Form4UiEvent.UpdateCO -> {
                _uiState.update { it.copy(co = event.value) }
            }
            is Form4UiEvent.UpdateCountry -> {
                _uiState.update { it.copy(country = event.value) }
            }
            is Form4UiEvent.UpdateDistrict -> {
                _uiState.update { it.copy(district = event.value) }
            }
            is Form4UiEvent.UpdateLGA -> {
                _uiState.update { it.copy(lga = event.value) }
            }
            is Form4UiEvent.UpdatePLNo -> {
                _uiState.update { it.copy(plNo = event.value) }
            }
            is Form4UiEvent.UpdatePOBox -> {
                _uiState.update { it.copy(poBox = event.value) }
            }
            is Form4UiEvent.UpdateState -> {
                _uiState.update { it.copy(state = event.value) }
            }

            Form4UiEvent.Proceed -> {

                if(_uiState.value.plNo.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your pl no") }
                    Log.d("JMDB","Enter your pl no")
                    return
                }

                if(_uiState.value.district.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your district") }
                    Log.d("JMDB","Enter your your district")
                    return
                }

                if(_uiState.value.country.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your country") }
                    Log.d("JMDB","Enter your country")
                    return
                }


                if(_uiState.value.state.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your state") }
                    Log.d("JMDB","Enter your state")
                    return
                }

                if(_uiState.value.lga.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your lga") }
                    Log.d("JMDB","Enter your lga")
                    return
                }

                if(_uiState.value.poBox.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your po box") }
                    Log.d("JMDB","Enter your po box")
                    return
                }


                if(_uiState.value.co.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your co") }
                    Log.d("JMDB","Enter your co")
                    return
                }


                Log.d("JMDB","form validated")
                _uiState.update { it.copy(loading = true) }

                val form4Body = Form4Body(
                    radditinal_address_info = _uiState.value.additionalInformation,
                    rcity_town = _uiState.value.state,
                    rco = _uiState.value.co,
                    rcountry = "Nigeria",
                    rdistrict = _uiState.value.district,
                    rhouse_no = "4746",
                    rpmb = _uiState.value.poBox,
                    rstate = _uiState.value.state,
                    rstreet_name = _uiState.value.additionalInformation,
                    track_no = "LVZRLK9O"
                )

                repository.submitForm4(form4Body,{
                    if(_uiState.value.loading) {
                    _uiState.update { it.copy(navigate = true) }
                    Log.d("JMDB","success") }
                }){error->
                    if(_uiState.value.loading) {
                    _uiState.update { it.copy(error = "error: $error", loading = false) }
                    Log.d("JMDB","error $error") }
                }

            }
            is Form4UiEvent.UpdateStreetName -> {
                _uiState.update { it.copy(streetName = event.value) }
            }
            Form4UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }


}