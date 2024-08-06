package com.smartapps.jmdb.registration.ui.screens.form1

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.registration.navigation.Routes
import com.smartapps.jmdb.registration.ui.screens.ErrorMessage
import com.smartapps.jmdb.registration.ui.screens.MyTextField
import com.smartapps.jmdb.registration.ui.screens.MyTextField2
import com.smartapps.jmdb.registration.ui.screens.MyTextFieldDark
import com.smartapps.jmdb.registration.ui.screens.MyTextFieldNum
import com.smartapps.jmdb.registration.ui.screens.form4.Circles
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.internal.wait
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 2000)
@Composable
fun Form1(modifier: Modifier = Modifier, uiState: StateFlow<Form1UiState> = MutableStateFlow(Form1UiState()), onEvent:(Form1UiEvent)->Unit = {}, navigate:(String)->Unit = {}) {

    val uiState = uiState.collectAsState()


        //JMDB MOBILE PORTAL
    //SMARTAPPS --- MOTTO
    //JOS LOGO AND MOTO



    if(uiState.value.navigate) {
        onEvent(Form1UiEvent.ClearNavigation)
        navigate(Routes.Form2.route)
    }


    Box {

        val dateDialogState  = rememberMaterialDialogState()





        val scrollState = rememberScrollState()

        Column(
            modifier = modifier.verticalScroll(scrollState)
        ) {


            Spacer(modifier = modifier.height(20.dp))


            Column(modifier = modifier
                .fillMaxWidth()
                .alpha(0.6f)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = modifier
                        .padding(bottom = 6.dp)
                        .fillMaxWidth()
                        , horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.jmdb),
                        contentDescription = null,
                        modifier = modifier
                            .size(50.dp)
                            .weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.jos),
                        contentDescription = null,
                        modifier = modifier
                            .size(70.dp)
                            .weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.jmdb),
                        contentDescription = null,
                        modifier = modifier
                            .size(50.dp)
                            .weight(1f)
                    )
                }

                Text(fontSize = 8.sp,text = "PLATEAU STATE OF NIGERIA", modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(fontSize = 8.sp, text = "Jos Metropolitan Development Board", modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            }
            Spacer(modifier = modifier.height(20.dp))
            Divider()

            Spacer(modifier = modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {



//                Image(painter = painterResource(R.drawable.round_arrow_back_ios_new_24), contentDescription = null, modifier = modifier
//                    .padding(start = 8.dp, end = 3.dp)
//                    .size(18.dp))




                    Text(
                        text = "BOX 1: APPLICANT",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(start = 10.dp)
                    )





                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = "1 of 6",
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    modifier = modifier.padding(top = 8.dp)
                )
                Spacer(modifier = modifier.width(20.dp))
            }
            Spacer(modifier = modifier.height(30.dp))

            Row(modifier = modifier.fillMaxWidth()) {
                Circles(modifier = modifier.weight(1f))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
            }

            Spacer(modifier = modifier.height(30.dp))

            Text(fontWeight = FontWeight.SemiBold,text = "(The person whose name would be on the Building Permission)", color = Color(
                0xFFDB0404
            ), fontSize = 12.5.sp, modifier = modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp))

            Spacer(modifier = modifier.height(15.dp))

            val str = buildAnnotatedString {
                withStyle(style = SpanStyle()){
                    append("All applicants ")
                }
                withStyle(style = SpanStyle(color = Color(0xFFDB0404))){
                    append("must complete ")
                }
                withStyle(style = SpanStyle()){
                    append("Box 1 in full. The Original identification document used to prove identity must be submitted, it will be copied and returned")
                }
            }

            Text(fontWeight = FontWeight.Medium,text = str, color = Color.DarkGray, fontSize = 10.sp, modifier = modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp))


            Spacer(modifier = modifier.height(35.dp))

            MyTextField2(
                label = "Title",
                value = uiState.value.title,
                items = listOf("Mr","Mrs"),
                callback = { x, y ->
                    onEvent(Form1UiEvent.UpdateTitle(y))
                })
            Spacer(modifier = modifier.height(15.dp))

            Spacer(modifier = modifier.height(15.dp))
            MyTextField(
                label = "First name",
                value = uiState.value.firstName,
                onEvent = {
                    onEvent(Form1UiEvent.UpdateFirstName(it))
                })
            Spacer(modifier = modifier.height(15.dp))
            MyTextField(
                label = "Middle name",
                value =  uiState.value.middleName,
                onEvent = {
                    onEvent(Form1UiEvent.UpdateMiddleName(it))
                })
            Spacer(modifier = modifier.height(15.dp))
            MyTextField(
                label = "Surname",
                value =  uiState.value.surname,
                onEvent = {
                    onEvent(Form1UiEvent.UpdateSurname(it))
                })

            Spacer(modifier = modifier.height(15.dp))

            MyTextFieldDark(
                label = "Tax Identification No. (TIN)",
                value = uiState.value.tin,
                onEvent = {
                    onEvent(Form1UiEvent.UpdateTin(it))
                })
            Spacer(modifier = modifier.height(15.dp))

            MyTextFieldDark(label = "Phone 1", value = uiState.value.phone1, onEvent = {
                onEvent(Form1UiEvent.UpdatePhone1(it))
            })
            Spacer(modifier = modifier.height(15.dp))

            MyTextFieldNum(label = "Phone 2", value = uiState.value.phone2, onEvent = {
                onEvent(Form1UiEvent.UpdatePhone2(it))
            })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField(label = "Email Address", value = uiState.value.email, onEvent = {
                onEvent(Form1UiEvent.UpdateEmail(it))
            })
            Spacer(modifier = modifier.height(15.dp))


            MyTextFieldNum(label = "ID Number", value = uiState.value.idNumber, onEvent = {
                onEvent(Form1UiEvent.UpdateIdNumber(it))
            })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField(label = "Occupation", value = uiState.value.occupation, onEvent = {
                onEvent(Form1UiEvent.UpdateOccupation(it))
            })
            Spacer(modifier = modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = modifier.width(25.dp))
                Image(
                    painter = painterResource(id = R.drawable.baseline_view_compact_24),
                    contentDescription = null,
                    modifier = modifier.size(18.dp)
                )
                Spacer(modifier = modifier.width(10.dp))
                Box(
                    modifier = modifier
                        .background(Color.LightGray)
                        .height(1.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.width(10.dp))
            }

            Spacer(modifier = modifier.height(15.dp))


            Text(text = "Date of Birth",fontSize = 12.sp, modifier = modifier.padding(start = 20.dp), color = Color.Gray)

            Row(modifier = modifier.fillMaxWidth()) {



                MyTextFieldNum(label = "Year", value = uiState.value.year, onEvent = {
                   onEvent(Form1UiEvent.UpdateYear(it))
                }, modifier = modifier.weight(1f), maxLength = 4)

                MyTextFieldNum(label = "Month", value = uiState.value.month, onEvent = {
                    onEvent(Form1UiEvent.UpdateMonth(it))
                }, modifier = modifier.weight(1f), maxLength = 2)

                MyTextFieldNum(label = "Day", value = uiState.value.day, onEvent = {
                    onEvent(Form1UiEvent.UpdateDay(it))

                }, modifier = modifier.weight(1f), maxLength = 2)




//                Box(modifier = modifier.fillMaxWidth().background(Color.Transparent).height(55.dp).clickable {
//                    dateDialogState.show()
//                }){
//
//                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Color(
//                        0xFF039BE5
//                    ), modifier = modifier
//                        .size(15.dp)
//                        .padding(end = 15.dp).align(Alignment.CenterEnd))
//
//            }


            }




            Spacer(modifier = modifier.height(15.dp))


            MyTextFieldDark(
                label = "Country",
                value = "Nigeria",
                onEvent = {

                })

            Spacer(modifier = modifier.height(15.dp))


            MyTextFieldDark(
                label = "State",
                value = "Plateau state",
                onEvent = {

                })

            Spacer(modifier = modifier.height(15.dp))

            MyTextField2(
                label = "L.G.A",
                value = uiState.value.lga,
                items = uiState.value.lgas!!.map { it.lga },
                callback = { x, y ->
                    onEvent(Form1UiEvent.UpdateLga(x, y))
                })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField2(
                label = "Identification Type",
                value = uiState.value.identification,
                items = uiState.value.identificationTypes.map { it.name },//JmdbRepository.identifications!!.data.map { it.name },
                callback = { x, y ->
                    onEvent(Form1UiEvent.UpdateIdentification(x, y))
                })


            Spacer(modifier = modifier.height(15.dp))
            Spacer(modifier = modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = modifier.width(25.dp))

                Text(text = "Gender", color = Color.Gray, fontSize = 12.sp)

                Spacer(modifier = modifier.width(10.dp))
                Box(
                    modifier = modifier
                        .background(Color.LightGray)
                        .height(1.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.width(10.dp))
            }


            Spacer(modifier = modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.clickable {
                    onEvent(Form1UiEvent.UpdateGender("Male"))
                }) {
                    RadioButton(onClick = {
                        onEvent(Form1UiEvent.UpdateGender("Male"))
                    }, selected = uiState.value.gender.equals("Male"))
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "Male", color = Color.Gray, fontSize = 12.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.clickable {
                    onEvent(Form1UiEvent.UpdateGender("Female"))
                }) {
                    RadioButton(onClick = {
                        onEvent(Form1UiEvent.UpdateGender("Female"))
                    }, selected = uiState.value.gender.equals("Female"))
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "Female", color = Color.Gray, fontSize = 12.sp)
                }


            }





            Spacer(modifier = modifier.height(10.dp))
            Spacer(modifier = modifier.height(15.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = modifier.width(15.dp))

                Text(text = "Application Type", color = Color.Gray, fontSize = 12.sp)

                Spacer(modifier = modifier.width(10.dp))
                Box(
                    modifier = modifier
                        .background(Color.LightGray)
                        .height(1.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.width(10.dp))
            }


            Spacer(modifier = modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .width(130.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateApplicationType("New Proposal"))
                        }
                ) {
                    RadioButton(
                        selected = uiState.value.applicationType == "New Proposal",
                        modifier = modifier.width(40.dp),
                        onClick = {
                            onEvent(Form1UiEvent.UpdateApplicationType("New Proposal"))
                        })
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "New Proposal", color = Color.Gray, fontSize = 12.sp)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .width(130.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateApplicationType("Renovation"))
                        }
                ) {
                    RadioButton(
                        selected = uiState.value.applicationType == "Renovation",
                        onClick = {
                            onEvent(Form1UiEvent.UpdateApplicationType("Renovation"))
                        },
                        modifier = modifier.width(40.dp)
                    )
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "Renovation", color = Color.Gray, fontSize = 12.sp)
                }
            }





            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .width(130.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateApplicationType("Remodeling"))
                        }
                ) {
                    RadioButton(
                        selected = uiState.value.applicationType == "Remodeling",
                        onClick = {
                            onEvent(Form1UiEvent.UpdateApplicationType("Remodeling"))
                        },
                        modifier = modifier.width(40.dp)
                    )
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "Remodeling", color = Color.Gray, fontSize = 12.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .width(130.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateApplicationType("Regularization"))
                        }
                ) {
                    RadioButton(
                        selected = uiState.value.applicationType == "Regularization",
                        onClick = {
                            onEvent(Form1UiEvent.UpdateApplicationType("Regularization"))
                        },
                        modifier = modifier.width(40.dp)
                    )
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "Regularization", color = Color.Gray, fontSize = 12.sp)
                }
            }

                Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .width(120.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateApplicationType("As-Built"))
                        }
                ) {
                    RadioButton(selected = uiState.value.applicationType == "As-Built", onClick = {
                        onEvent(Form1UiEvent.UpdateApplicationType("As-Built"))
                    }, modifier = modifier.width(40.dp))
                    Spacer(modifier = modifier.width(0.dp))
                    Text(text = "As-Built", color = Color.Gray, fontSize = 12.sp)
                }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .width(120.dp)
                            .padding(start = 10.dp)
                            .clickable {
                                onEvent(Form1UiEvent.ShowOther)
                            }
                    ) {
                        RadioButton(selected = uiState.value.showOther, onClick = {
                            onEvent(Form1UiEvent.ShowOther)
                        }, modifier = modifier.width(40.dp))
                        Spacer(modifier = modifier.width(0.dp))
                        Text(text = "Other", color = Color.Gray, fontSize = 12.sp)
                    }

            }



            if (uiState.value.showOther) {
                Spacer(modifier = modifier.height(5.dp))
                MyTextField(
                    label = "Type here", value = if (uiState.value.showOther) {
                        uiState.value.applicationType
                    } else {
                        ""
                    }, onEvent = {
                        onEvent(Form1UiEvent.UpdateApplicationType(it))
                    })
            }

            Spacer(modifier = modifier.height(20.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = modifier.width(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.baseline_view_compact_24),
                    contentDescription = null,
                    modifier = modifier.size(18.dp)
                )
                Spacer(modifier = modifier.width(10.dp))
                Box(
                    modifier = modifier
                        .background(Color.LightGray)
                        .height(1.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.width(10.dp))
            }


            Spacer(modifier = modifier.height(5.dp))


            Spacer(modifier = modifier.height(30.dp))



            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .padding(bottom = 20.dp, start = 7.dp, end = 7.dp)
                    .clip(
                        RoundedCornerShape(5.dp)
                    )
                    .background(Color(0xFF4CAF50))
                    .clickable {
                        onEvent(Form1UiEvent.Proceed)
                    }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }




        }



        var pickedDate by remember {
            mutableStateOf(LocalDate.now())
        }

        val formattedDate by remember {
            derivedStateOf {
                DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
            }
        }





        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok", textStyle = TextStyle(color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)){
                }
                negativeButton(text = "Close", textStyle = TextStyle(color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold))
            },

        ){
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = Color(0xFF4CAF50),
                    dateActiveBackgroundColor = Color(0xFF4CAF50)
                )
            ){
                pickedDate = it
                onEvent(Form1UiEvent.UpdateDob(it.year.toString() + "-" + it.month + "-" + it.dayOfMonth))
            }
        }


        ErrorMessage(text = uiState.value.error, modifier = modifier){
            onEvent(Form1UiEvent.ClearError)}





        if(uiState.value.loading) {
            Dialog(onDismissRequest = { }) {
                Box(modifier = modifier.fillMaxSize()) {

                    //add cancel image here

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF4CAF50)
                        )
                        Spacer(modifier = modifier.height(8.dp))
                        Text(text = "Saving Box 1...",fontSize = 12.sp,color = Color.White, fontWeight = FontWeight.SemiBold, modifier = modifier.clickable {
                            onEvent(Form1UiEvent.UpdateLoading)
                        })
                    }



                    Text("Cancel", modifier = modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp)
                        .clickable {
                            onEvent(Form1UiEvent.UpdateLoading)
                        }, fontWeight = FontWeight.Bold, fontSize = 15.5.sp, fontStyle = FontStyle.Italic)

                }
            }
        }





    }




}