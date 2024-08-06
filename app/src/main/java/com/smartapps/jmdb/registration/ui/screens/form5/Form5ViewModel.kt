package com.smartapps.jmdb.registration.ui.screens.form5

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form5Body
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Form5ViewModel(
    val repository: JmdbRepository
): ViewModel()  {


    private val _uiState = MutableStateFlow(Form5UiState())
    val uiState: StateFlow<Form5UiState> = _uiState

    init {

        _uiState.update { it.copy(skipState = Constants.SKIP) }

        CoroutineScope(Dispatchers.IO).launch {


            repository.getLandPurposeItems{
                val x = it

                repository.getLandUses{
                    val y = it
                    repository.getLgas{
                        val w = it
                        _uiState.update { it.copy(landPurposes = x, landUses = y, lgas = w) }
                    }
                }
            }






        }



    }

    fun onEvent(event: Form5UiEvent){

        when(event){
            Form5UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }

            Form5UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
            Form5UiEvent.Proceed -> {

//                if(_uiState.value.landUse.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter land use") }
//                    Log.d("JMDB","Enter land use")
//                    return
//                }

                if(_uiState.value.district.isEmpty()){
                    _uiState.update { it.copy(error = "Enter district") }
                    Log.d("JMDB","Enter district")
                    return
                }

                if(_uiState.value.description.isEmpty()){
                    _uiState.update { it.copy(error = "Enter description") }
                    Log.d("JMDB","Enter description")
                    return
                }


//                if(_uiState.value.purpose.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter land purpose") }
//                    Log.d("JMDB","Enter land purpose")
//                    return
//                }

                if(_uiState.value.lga.isEmpty()){
                    _uiState.update { it.copy(error = "Enter lga") }
                    Log.d("JMDB","Enter lga")
                    return
                }


                Log.d("JMDB","form validated")
             //   _uiState.update { it.copy(loading = true) }

                //distance
                //light
                //safe


                val form5Body = Form5Body(
                    purpose = _uiState.value.purpose,
                    plot__land_use = _uiState.value.landUse,
                    plot_district = _uiState.value.district,
                    plot_lga = _uiState.value.lga,
                    plot_address_discription = _uiState.value.description,
                    track_no = Constants.TRACK_NO
                )

                JmdbRepository.FORM_5 = form5Body
                _uiState.update { it.copy(navigate = true, loading = false) }

//                repository.submitForm5(form5Body, {
//                    TRACK_NO = it.track_no
//                    if(_uiState.value.loading) {
//                    _uiState.update { it.copy(navigate = true, loading = false) }
//                    Log.d("JMDB","success") }
//                }){error ->
//                    if(_uiState.value.loading) {
//                    Log.d("JMDB","error: $error")
//                    _uiState.update { it.copy(error = "error: $error", loading = false) } }
//                }


            }
            is Form5UiEvent.UpdateDescription -> {
                _uiState.update { it.copy(description = event.value) }
            }
            is Form5UiEvent.UpdateDistrict -> {
                _uiState.update { it.copy(district = event.value) }
            }

            is Form5UiEvent.UpdateLandUse -> {
                _uiState.update { it.copy(landUse = event.value) }
            }
            is Form5UiEvent.UpdateLga -> {
                _uiState.update { it.copy(lga = event.value) }
            }

            is Form5UiEvent.UpdatePurpose -> {
                _uiState.update { it.copy(purpose = event.value) }
            }
            Form5UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }
        }

    }


}