package com.smartapps.jmdb.ui.screens.form1

import android.util.Log
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.model.Form1Body
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.OWNER_EMAIL
import com.smartapps.jmdb.enumeration.data.repository.OWNER_MIDDLE_NAME
import com.smartapps.jmdb.enumeration.data.repository.OWNER_NAME
import com.smartapps.jmdb.enumeration.data.repository.OWNER_NUMBER
import com.smartapps.jmdb.enumeration.data.repository.OWNER_NUMBER2
import com.smartapps.jmdb.enumeration.data.repository.OWNER_SURNAME
import com.smartapps.jmdb.enumeration.data.repository.OWNER_TITLE
import com.smartapps.jmdb.enumeration.data.repository.TIN

import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
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

    init {

        CoroutineScope(Dispatchers.IO).launch {


            val x = repository.getLgas()
            val i = repository.getIdentificationItems()

            /*
            val OWNER_EMAIL = "johntombra29@gmail.com"
val  OWNER_NUMBER = "08132758276"
val  OWNER_NUMBER2 = "07048876634"
val OWNER_NAME = "Tombra"
val OWNER_MIDDLE_NAME = "Marvel"
val OWNER_SURNAME = "John"
val OWNER_TITLE = "Mr"
val TIN = "08132758276"
            */

            _uiState.update { it.copy(email = OWNER_EMAIL,tin = TIN,middleName = OWNER_MIDDLE_NAME, phone1 = OWNER_NUMBER, phone2 = OWNER_NUMBER2,firstName = OWNER_NAME,title = OWNER_TITLE, surname = OWNER_SURNAME) }

            CoroutineScope(Dispatchers.Main).launch {

                _uiState.update { it.copy(lgas = x, identificationTypes = i)

                }
            }
        }



    }

    fun onEvent(event: Form1UiEvent){

        when(event){
            Form1UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
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

                if(_uiState.value.middleName.isEmpty()){
                    _uiState.update { it.copy(error = "Enter Middle name") }
                  //  Log.d("JMDB","Enter your first name")
                    return
                }

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


                if(_uiState.value.phone2.isEmpty()){
                    _uiState.update { it.copy(error = "Enter phone number 2") }
                    Log.d("JMDB","Enter phone number 2")
                    return
                }

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

                if(_uiState.value.dob.isEmpty()){
                    _uiState.update { it.copy(error = "Select date of birth") }
                    Log.d("JMDB","Select date of birth")
                    return
                }



                if(_uiState.value.nationality.isEmpty()){
                    _uiState.update { it.copy(error = "Select nationality") }
                    Log.d("JMDB","Select nationality")
                    return
                }



                                if(_uiState.value.state.isEmpty()){
                                    _uiState.update { it.copy(error = "Select state") }
                                    Log.d("JMDB","Select state")
                                    return
                                }


                                if(_uiState.value.lga.isEmpty()){
                                    _uiState.update { it.copy(error = "Select lga") }
                                    Log.d("JMDB","Select lga")
                                    return
                                }

//               if(_uiState.value.identification.isEmpty()){
//                    _uiState.update { it.copy(error = "Enter identification") }
//                    Log.d("JMDB","Enter identification")
//                    return
//                }




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

                _uiState.update { it.copy(loading = true) }




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
                    dob = _uiState.value.dob,
                    email = _uiState.value.email,
                    fn = _uiState.value.firstName,
                    gender = _uiState.value.gender,
                    id_number = _uiState.value.idNumber,
                    identification = _uiState.value.identification,
                    lga = _uiState.value.lga,
                    mn = if(_uiState.value.middleName.isEmpty()){"Not set"}else{_uiState.value.middleName},
                    nationality = _uiState.value.nationality,
                    occupation = _uiState.value.occupation,
                    phone1 = _uiState.value.phone1,
                    phone2 = _uiState.value.phone2,
                    sn = _uiState.value.surname,
                    soo = "1",
                    tin = _uiState.value.tin,
                    title = _uiState.value.title,
                    track_no = "12338847484884",
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

                repository.submitForm1(form1Body,{

                    if(_uiState.value.loading) {

                        Log.d("JMDB", "success")
                        _uiState.update { it.copy(navigate = true) }
                    }

                }){error->
                    if(_uiState.value.loading) {
                    Log.d("JMDB","error $error")
                    _uiState.update { it.copy(loading = false, error=error) } }
                }


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
        }

    }


}