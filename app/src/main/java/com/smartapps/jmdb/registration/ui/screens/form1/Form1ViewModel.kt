package com.smartapps.jmdb.registration.ui.screens.form1

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form1Body

import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository

import com.smartapps.jmdb.enumeration.util.Constants

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class Form1ViewModel(
    val repository: JmdbRepository
): ViewModel() {



    private val _uiState = MutableStateFlow(Form1UiState())
    val uiState: StateFlow<Form1UiState> = _uiState


   // val months = listOf("January", "February","March","April","May","June","July","August","September","October","November","December")

    //val years = listOf("2024","2023","","","","","","","","","","","","","","","","","","")


    val _31Days = (1..31).toList()
    val _30Days = (1..3).toList()
    val _29Days = (1..29).toList()
    val _28Days = (1..28).toList()


    init {


       // _uiState.update { it.copy(months = months, years = years) }



             repository.getLgas{
                 val x = it

                 repository.getIdentificationItems{
                     val i = it
                     _uiState.update { it.copy(tin = Constants.TIN,lgas = x, identificationTypes = i) }
                 }
            }


    }

    fun onEvent(event: Form1UiEvent){

        when(event){
            Form1UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    CoroutineScope(Dispatchers.Main).launch {
                    _uiState.update { it.copy(error = null) }
                    }
                }
            }

            Form1UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }

            Form1UiEvent.Proceed -> {

                if(_uiState.value.title.isEmpty()){
                    _uiState.update { it.copy(error = "Enter title") }
                //    Log.d("JMDB","Enter your first name")
                    return
                }

                if(_uiState.value.firstName.isEmpty()){
                    _uiState.update { it.copy(error = "Enter first name") }
                    Log.d("JMDB","Enter your first name")
                    return
                }

//                if(_uiState.value.middleName.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter Middle name") }
//                  //  Log.d("JMDB","Enter your first name")
//                    return
//                }

                if(_uiState.value.surname.isEmpty()){
                    _uiState.update { it.copy(error = "Enter surname name") }
                   // Log.d("JMDB","Enter your first name")
                    return
                }

                if(_uiState.value.tin.isEmpty()){
                    _uiState.update { it.copy(error = "Enter tax identification number") }
                    Log.d("JMDB","Enter your identification number")
                    return
                }

                if(_uiState.value.phone1.isEmpty()){
                    _uiState.update { it.copy(error = "Enter phone number 1") }
                    Log.d("JMDB","Enter phone number 1")
                    return
                }


//                if(_uiState.value.phone2.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter phone number 2") }
//                    Log.d("JMDB","Enter phone number 2")
//                    return
//                }

                if(_uiState.value.email.isEmpty()){
                    _uiState.update { it.copy(error = "Enter email") }
                    Log.d("JMDB","Enter email")
                    return
                }


              if(_uiState.value.idNumber.isEmpty()){
                    _uiState.update { it.copy(error = "Enter ID Number") }
                    Log.d("JMDB","EnterID Number")
                    return
                }


                if(_uiState.value.occupation.isEmpty()){
                    _uiState.update { it.copy(error = "Enter occupation") }
                    Log.d("JMDB","Enter occupation")
                    return
                }

                if(_uiState.value.year.isEmpty()){
                    _uiState.update { it.copy(error = "Enter year of birth") }
                    Log.d("JMDB","Select date of birth")
                    return
                }

                if(_uiState.value.year.length < 4){
                    _uiState.update { it.copy(error = "Year of birth must have 4 characters") }
                    Log.d("JMDB","Select date of birth")
                    return
                }

                if(_uiState.value.month.isEmpty()){
                    _uiState.update { it.copy(error = "Enter month of birth") }
                    Log.d("JMDB","Select date of birth")
                    return
                }

//                if(_uiState.value.month.length < 2){
//                    _uiState.update { it.copy(error = "Month of birth must have 2 characters") }
//                    Log.d("JMDB","Select date of birth")
//                    return
//                }

                if(_uiState.value.day.isEmpty()){
                    _uiState.update { it.copy(error = "Enter day of birth") }
                    Log.d("JMDB","Select date of birth")
                    return
                }


//                if(_uiState.value.day.length < 2){
//                    _uiState.update { it.copy(error = "Day of birth must have 2 characters") }
//                    Log.d("JMDB","Select date of birth")
//                    return
//                }


//                if(_uiState.value.nationality.isEmpty()){
//                    _uiState.update { it.copy(error = "Select nationality") }
//                    Log.d("JMDB","Select nationality")
//                    return
//                }



//                                if(_uiState.value.state.isEmpty()){
//                                    _uiState.update { it.copy(error = "Select state") }
//                                    Log.d("JMDB","Select state")
//                                    return
//                                }


                                if(_uiState.value.lga.isEmpty()){
                                    _uiState.update { it.copy(error = "Select lga") }
                                    Log.d("JMDB","Select lga")
                                    return
                                }

               if(_uiState.value.identification.isEmpty()){
                    _uiState.update { it.copy(error = "Select identification type") }
                    Log.d("JMDB","Enter identification")
                    return
                }




                if(_uiState.value.gender.isEmpty()){
                    _uiState.update { it.copy(error = "Select gender") }
                    Log.d("JMDB","Select gender")
                    return
                }


                if(_uiState.value.applicationType.isEmpty()){
                    _uiState.update { it.copy(error = "Select application type") }
                    Log.d("JMDB","Select application type")
                    return
                }

                Log.d("JMDB","validation complete")


                val date = _uiState.value.year.toString() + "-" + _uiState.value.month.toString() + "-" + _uiState.value.day.toString()




             /*   "application_type": "New Proposal",
                "title": "Mr",
                "fn": "Jude",
                "mn": "",
                "sn": "Paul",
                "gender": "Male",
                "dob": "22/04/1997",
                "occupation": "Developer",
                "nationality": "Nigeria",
                "soo": "1",
                "lga": "1",
                "phone1": "08136759553",
                "phone2": "",
                "phone3": "",
                "email": "ikwobejude@gmail.com",
                "tin": "23320765908",
                "identification": "nationality ID",
                "id_number": "5757588577575",
                "track_no": "12338847484884" // optional at first*/


            //    Log.d("MY_BODY",_uiState.value.toString())


                val form1Body = Form1Body(
                    application_type = _uiState.value.applicationType,
                    dob = date,
                    email = _uiState.value.email,
                    fn = _uiState.value.firstName,
                    gender = _uiState.value.gender,
                    id_number = _uiState.value.idNumber,
                    identification = _uiState.value.identification,
                    lga = _uiState.value.lga,
                    mn = if(_uiState.value.middleName.isEmpty()){"Not set"}else{_uiState.value.middleName},
                    nationality = "Nigerian",
                    occupation = _uiState.value.occupation,
                    phone1 = _uiState.value.phone1,
                    phone2 = _uiState.value.phone2,
                    sn = _uiState.value.surname,
                    soo = "32",// incorrect
                    tin = _uiState.value.tin,
                    title = _uiState.value.title,
                    track_no = Constants.TRACK_NO,//"12338847484884",
                    phone3 = ""
                )

                Log.d("MY_BODY",form1Body.toString())

//                val form1Body = Form1Body(
//                    application_type = _uiState.value.applicationType,
//                    dob = _uiState.value.dob,
//                    email = _uiState.value.email,
//                    fn = _uiState.value.firstName, //todo
//                    gender = _uiState.value.gender,
//                    id_number = _uiState.value.idNumber,
//                    identification = "NIN",//_uiState.value.identification,
//                    lga = _uiState.value.lgas!!.filter { it.lga == _uiState.value.lga.toString() }[0].lga_id.toString(),
//                    mn = _uiState.value.middleName,//TODO
//                    nationality = "Nigeria",
//                    occupation = _uiState.value.occupation,
//                    phone1 = _uiState.value.phone1,
//                    phone2 = _uiState.value.phone2,
//                    sn = _uiState.value.surname,//TODO
//                    soo = "234",//TODO
//                    tin = _uiState.value.tin,
//                    title = _uiState.value.title, //TODO
//                    track_no = System.currentTimeMillis().toString(), //TODO
//                    phone3 = ""
//                )

                JmdbRepository.FORM_1 = form1Body
                _uiState.update { it.copy(navigate = true, loading = false) }

//                _uiState.update { it.copy(loading = true) }
//
//                repository.submitForm1(form1Body,{
//                    TRACK_NO = it.track_no
//                    if(_uiState.value.loading) {
//
//                        Log.d("JMDB", "success")
//                        _uiState.update { it.copy(navigate = true, loading = false) }
//                    }
//
//                }){error->
//                    if(_uiState.value.loading) {
//                    Log.d("JMDB","error $error")
//                    _uiState.update { it.copy(loading = false, error=error) } }
//                }
//

            }

            is Form1UiEvent.UpdateApplicationType -> {

                if (!_uiState.value.showOther) {
                    _uiState.update {
                        it.copy(
                            showOther = false
                        )
                    }
                }

                _uiState.update { it.copy(applicationType = event.value) }
            }
            is Form1UiEvent.UpdateDob -> {
                _uiState.update { it.copy(dob = event.value) }
            }
            is Form1UiEvent.UpdateEmail -> {
                _uiState.update { it.copy(email = event.value) }
            }
            is Form1UiEvent.UpdateGender -> {
                _uiState.update { it.copy(gender = event.value) }
            }
            is Form1UiEvent.UpdateIdNumber -> {
                _uiState.update { it.copy(idNumber = event.value) }
            }

            is Form1UiEvent.UpdateIdentification -> {
                _uiState.update { it.copy(identification = event.value) }
            }
            is Form1UiEvent.UpdateLga -> {
                _uiState.update { it.copy(lga = event.value) }
            }
            is Form1UiEvent.UpdateNationality -> {
                _uiState.update { it.copy(nationality = event.value) }
            }

            is Form1UiEvent.UpdateOccupation -> {
                _uiState.update { it.copy(occupation = event.value) }
            }

            is Form1UiEvent.UpdatePhone1 -> {
                _uiState.update { it.copy(phone1 = event.value) }
            }
            is Form1UiEvent.UpdatePhone2 -> {
                _uiState.update { it.copy(phone2 = event.value) }
            }
            is Form1UiEvent.UpdateState -> {
                _uiState.update { it.copy(state = event.value) }
            }
            is Form1UiEvent.UpdateTin -> {
                _uiState.update { it.copy(tin = event.value) }
            }
            Form1UiEvent.ShowOther -> {
                _uiState.update { it.copy(showOther = !_uiState.value.showOther) }
            }

            Form1UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }

            }

            is Form1UiEvent.UpdateFirstName -> {
                _uiState.update { it.copy(firstName = event.value) }
            }
            is Form1UiEvent.UpdateMiddleName -> {
                _uiState.update { it.copy(middleName = event.value) }
            }
            is Form1UiEvent.UpdateSurname -> {
                _uiState.update { it.copy(surname = event.value) }
            }
            is Form1UiEvent.UpdateTitle -> {
                _uiState.update { it.copy(title = event.value) }
            }
            is Form1UiEvent.UpdateDay -> {
                _uiState.update { it.copy(day = event.value) }
            }
            is Form1UiEvent.UpdateMonth -> {
                _uiState.update { it.copy(month = event.value) }
            }
            is Form1UiEvent.UpdateYear -> {
                _uiState.update { it.copy(year = event.value) }
            }
        }

    }


}