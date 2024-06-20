package com.smartapps.jmdb.ui.screens.form2

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form2Body
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Form2ViewModel(
    val repository: JmdbRepository
): ViewModel()  {



    private val _uiState = MutableStateFlow(Form2UiState())
    val uiState: StateFlow<Form2UiState> = _uiState

    init {

        CoroutineScope(Dispatchers.IO).launch {


            val x = repository.getLgas()



            CoroutineScope(Dispatchers.Main).launch {

                _uiState.update { it.copy(lgas = x)


                }
            }
        }



    }

    fun onEvent(event: Form2UiEvent){

        when(event){
            Form2UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }

            Form2UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
            is Form2UiEvent.UpdateAdditionalInformation -> {
                _uiState.update { it.copy(additionalInformation = event.value) }
            }
            is Form2UiEvent.UpdateCO -> {
                _uiState.update { it.copy(co = event.value) }
            }
            is Form2UiEvent.UpdateCountry -> {
                _uiState.update { it.copy(country = event.value) }
            }
            is Form2UiEvent.UpdateDistrict -> {
                _uiState.update { it.copy(district = event.value) }
            }
            is Form2UiEvent.UpdateLGA -> {
                _uiState.update { it.copy(lga = event.value) }
            }
            is Form2UiEvent.UpdatePLNo -> {
                _uiState.update { it.copy(plNo = event.value) }
            }
            is Form2UiEvent.UpdatePOBox -> {
                _uiState.update { it.copy(poBox = event.value) }
            }
            is Form2UiEvent.UpdateState -> {
                _uiState.update { it.copy(state = event.value) }
            }

            Form2UiEvent.Proceed -> {



                if(_uiState.value.plNo.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your PL No.") }
                    Log.d("JMDB","Enter your PL No.")
                    return
                }

                if(_uiState.value.district.isEmpty()){
                    _uiState.update { it.copy(error = "Enter  district") }
                    Log.d("JMDB","Enter district")
                    return
                }

                if(_uiState.value.country.isEmpty()){
                    _uiState.update { it.copy(error = "Select country") }
                    Log.d("JMDB","Select country")
                    return
                }

                if(_uiState.value.state.isEmpty()){
                    _uiState.update { it.copy(error = "Enter state") }
                    Log.d("JMDB","Enter state")
                    return
                }

                if(_uiState.value.lga.isEmpty()){
                    _uiState.update { it.copy(error = "Enter lga") }
                    Log.d("JMDB","Enter lga")
                    return
                }


                if(_uiState.value.poBox.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your po box") }
                    Log.d("JMDB","Enter your po box")
                    return
                }

                if(_uiState.value.streetName.isEmpty()){
                    _uiState.update { it.copy(error = "Enter street name") }
                 //   Log.d("JMDB","Enter your po box")
                    return
                }

                if(_uiState.value.co.isEmpty()){
                    _uiState.update { it.copy(error = "Enter your co") }
                    Log.d("JMDB","Enter your co")
                    return
                }


                Log.d("JMDB","validation complete")

                _uiState.update { it.copy(loading = true) }


                val form2Body = Form2Body(
                    house_no = "100",
                    street_name = uiState.value.streetName,
                    district = _uiState.value.district,
                    co = _uiState.value.co,
                    addition_address_info = _uiState.value.additionalInformation,
                    pmb =_uiState.value.poBox,
                    track_no = "LVZRLK9O"
                )

                repository.submitForm2(form2Body,{
                    if(_uiState.value.loading) {
                        Log.d("JMDB", "success")
                        _uiState.update { it.copy(navigate = true) }
                    }
                }){ error ->
                    if(_uiState.value.loading) {
                    Log.d("JMDB","error $error")
                    _uiState.update { it.copy(error = error, loading = false) } }
                }


            }

            is Form2UiEvent.UpdateStreetName -> {
                _uiState.update { it.copy(streetName = event.value) }
            }
            Form2UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }
        }

    }

}