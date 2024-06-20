package com.smartapps.jmdb.ui.screens.form2

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.navigation.Routes
import com.smartapps.jmdb.ui.screens.ErrorMessage
import com.smartapps.jmdb.ui.screens.form4.Circles
import com.smartapps.jmdb.ui.screens.MyTextField
import com.smartapps.jmdb.ui.screens.MyTextField2
import com.smartapps.jmdb.ui.screens.MyTextFieldNum
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.form1.Form1UiState
import com.smartapps.jmdb.ui.screens.form3.Form3UiEvent
import com.smartapps.jmdb.ui.screens.form3.Form3UiState
import com.smartapps.jmdb.ui.screens.form6.Form6UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 2000)
@Composable
fun Form2(modifier: Modifier = Modifier, uiState: StateFlow<Form2UiState> = MutableStateFlow(Form2UiState()), onEvent:(Form2UiEvent)->Unit = {}, navigate:(String)->Unit = {}) {

    val uiState = uiState.collectAsState()


    if(uiState.value.navigate) {
        onEvent(Form2UiEvent.ClearNavigation)
        navigate(Routes.Form3.route)
    }

    Box {


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



//                Box(
//                    modifier = modifier
//                        .width(170.dp)
//                        .height(45.dp)
//                        .padding(start = 7.dp, end = 7.dp)
//                        .clip(
//                            RoundedCornerShape(5.dp)
//                        )
//                       , contentAlignment = Alignment.Center
//                ) {
                    Text(
                        text = "BOX 2: ADDRESS",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(start = 10.dp)
                    )
            //    }




                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = "2 of 6",
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
                Circles(modifier = modifier.weight(1f))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
                Circles(modifier = modifier.weight(1f),color = Color(0xFFDDDDDD))
            }

            Spacer(modifier = modifier.height(30.dp))

//            Text(fontWeight = FontWeight.SemiBold,text = "(The person whose name would be on the Building Permission)", color = Color(
//                0xFFDB0404
//            ), fontSize = 12.5.sp, modifier = modifier
//                .fillMaxWidth()
//                .padding(start = 5.dp, end = 5.dp))
//
//            Spacer(modifier = modifier.height(15.dp))

            val str = buildAnnotatedString {
                withStyle(style = SpanStyle()){
                    append("All applicants ")
                }
                withStyle(style = SpanStyle(color = Color(0xFFDB0404))){
                    append("must complete ")
                }
                withStyle(style = SpanStyle()){
                    append("Box 2 in full. This should be your normal residential address")
                }
            }

            Text(fontWeight = FontWeight.Medium,text = str, color = Color.DarkGray, fontSize = 10.sp, modifier = modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp))



            Spacer(modifier = modifier.height(30.dp))


            MyTextField(label = "PL No.", value = uiState.value.plNo, onEvent = {
                onEvent(Form2UiEvent.UpdatePLNo(it))
            })
            Spacer(modifier = modifier.height(15.dp))

            MyTextField(label = "District", value = uiState.value.district, onEvent = {
                onEvent(Form2UiEvent.UpdateDistrict(it))
            })
            Spacer(modifier = modifier.height(15.dp))

            MyTextField(label = "Street Name", value = uiState.value.streetName, onEvent = {
                onEvent(Form2UiEvent.UpdateStreetName(it))
            })
            Spacer(modifier = modifier.height(15.dp))

            MyTextField2(
                label = "Country",
                value = uiState.value.country,
                items = listOf("Nigeria"),
                callback = { x, y ->
                    onEvent(Form2UiEvent.UpdateCountry(y))
                })
            Spacer(modifier = modifier.height(15.dp))

            MyTextField2(
                label = "State",
                value = uiState.value.state,
                items = listOf("Jos"),
                callback = { x, y ->
                    onEvent(Form2UiEvent.UpdateState(y))
                })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField2(
                label = "L.G.A",
                value = uiState.value.lga,
                items = uiState.value.lgas.map { it.lga },
                callback = { x, y ->
                    onEvent(Form2UiEvent.UpdateLGA(y))
                })
            Spacer(modifier = modifier.height(15.dp))



            MyTextField(label = "P.O Box", value = uiState.value.poBox, onEvent = {
                onEvent(Form2UiEvent.UpdatePOBox(it))
            })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField(label = "C/O", value = uiState.value.co, onEvent = {
                onEvent(Form2UiEvent.UpdateCO(it))
            })
            Spacer(modifier = modifier.height(15.dp))


            MyTextField(
                label = "Additional Address Information",
                value = uiState.value.additionalInformation,
                modifier = modifier.height(100.dp),
                onEvent = {
                    onEvent(Form2UiEvent.UpdateAdditionalInformation(it))
                })

            Spacer(modifier = modifier.height(30.dp))



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
                        onEvent(Form2UiEvent.Proceed)
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

        ErrorMessage(text = uiState.value.error, modifier = modifier){
            onEvent(Form2UiEvent.ClearError)}


        if(uiState.value.loading) {
            Dialog(onDismissRequest = { }) {
                Box(modifier = modifier.fillMaxSize()) {

                    //add cancel image here

                    Column(
                        modifier = modifier.fillMaxWidth().align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF4CAF50)
                        )
                        Spacer(modifier = modifier.height(8.dp))
                        Text(text = "Saving step 2...",fontSize = 12.sp,color = Color.White, fontWeight = FontWeight.SemiBold, modifier = modifier.clickable {
                            onEvent(Form2UiEvent.UpdateLoading)
                        })
                    }



                    Text("Cancel", modifier = modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp).clickable {
                        onEvent(Form2UiEvent.UpdateLoading)
                    }, fontWeight = FontWeight.Bold, fontSize = 15.5.sp, fontStyle = FontStyle.Italic)

                }
            }
        }


    }



}