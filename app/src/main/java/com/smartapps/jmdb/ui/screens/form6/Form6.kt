package com.smartapps.jmdb.ui.screens.form6


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
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.navigation.Routes
import com.smartapps.jmdb.ui.screens.ErrorMessage
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.form1.Form1UiState
import com.smartapps.jmdb.ui.screens.form4.Circles
import com.smartapps.jmdb.ui.screens.form5.Form5UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



@Preview(showBackground = true, heightDp = 2000)
@Composable
fun Form6(modifier: Modifier = Modifier, uiState: StateFlow<Form6UiState> = MutableStateFlow(Form6UiState()), onEvent:(Form6UiEvent)->Unit = {}, select: (Int)->Unit = {}, navigate:(String)->Unit = {}, congrats: ()->Unit = {}) {

    val uiState = uiState.collectAsState()

    if(uiState.value.navigate) {
        onEvent(Form6UiEvent.ClearNavigation)
        congrats()
    }

    Box {


        if(uiState.value.navigate) {
//            onEvent(Form6UiEvent.ClearNavigation)
        //    navigate(Routes.Form6.route)
            //TODO GO TO SUCCESS PAGE
        }

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
                        text = "BOX 6: DOCUMENTS",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(start = 10.dp)
                    )





                    Spacer(modifier = modifier.weight(1f))

                    Text(
                        text = "6 of 6",
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
                    Circles(modifier = modifier.weight(1f))
                    Circles(modifier = modifier.weight(1f))
                    Circles(modifier = modifier.weight(1f))
                    Circles(modifier = modifier.weight(1f))
                }

                Spacer(modifier = modifier.height(30.dp))

//                Text(fontWeight = FontWeight.SemiBold,text = "(The person whose name would be on the Building Permission)", color = Color(
//                    0xFFDB0404
//                ), fontSize = 12.5.sp, modifier = modifier
//                    .fillMaxWidth()
//                    .padding(start = 5.dp, end = 5.dp))
//
//                Spacer(modifier = modifier.height(15.dp))

                val str = buildAnnotatedString {
                    withStyle(style = SpanStyle()){
                        append("Applicants ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFDB0404))){
                        append("should submit ")
                    }
                    withStyle(style = SpanStyle()){
                        append("all the relevant documents, with minimum requirement indicated below.\n\nIf you have multiple relevant documents, please submit the,. and tick the documents that your acquire.\n\nAll drawings should be endorsed by a relevant professional")
                    }
                }

                Text(fontWeight = FontWeight.Medium,text = str, color = Color.DarkGray, fontSize = 10.sp, modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp))



                Spacer(modifier = modifier.height(30.dp))



            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .clickable {
                    select(1)
                }, contentAlignment = Alignment.Center) {



                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of the certificate of occupancy", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_1.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_1.isNotEmpty()) {
                    Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                   }

                }

            }
            Spacer(modifier = modifier.height(10.dp))


            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(2)
                }, contentAlignment = Alignment.Center) {





                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of MLS TP Acknowledge Letter", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_2.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_2.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }



            }
            Spacer(modifier = modifier.height(10.dp))



            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(3)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of Structural Drawing and Details", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_3.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_3.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }

            }
            Spacer(modifier = modifier.height(10.dp))


            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(4)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of Structural Calculations", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_4.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_4.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))



            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(5)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of Architectural Drawing and Details", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_5.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_5.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }


            }
            Spacer(modifier = modifier.height(10.dp))








            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(6)
                }, contentAlignment = Alignment.Center) {



                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Copy of Mechanical Electrical Drawings and Details", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_6.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_6.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))





            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(7)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){


                    Text(text = "Site Analysis Report", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_7.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_7.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))





            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(8)
                }, contentAlignment = Alignment.Center) {



                Row(verticalAlignment = Alignment.CenterVertically){

                    Text(text = "Copy of EIAR", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_8.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_8.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))





            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(9)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){

                    Text(text = "Copy of BLOCK Plan", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_9.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_9.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))





            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(10)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){

                    Text(text = "Soil Investigation Report", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_10.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_10.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
            Spacer(modifier = modifier.height(10.dp))





            Box(modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF1F1F1))
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    select(11)
                }, contentAlignment = Alignment.Center) {


                Row(verticalAlignment = Alignment.CenterVertically){

                    Text(text = "Copy of Service Approvals", fontSize = 13.sp, color = if(JmdbRepository.IMAGE_11.isNotEmpty()){Color(0xFF4CAF50)}else{
                        Color.DarkGray
                    })

                    if(JmdbRepository.IMAGE_11.isNotEmpty()) {
                        Spacer(modifier = modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.round_verified_24),
                            contentDescription = null,
                            modifier = modifier.size(15.dp)
                        )
                    }

                }
            }
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
                            onEvent(Form6UiEvent.Proceed)
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Finish",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

        }






        ErrorMessage(text = uiState.value.error, modifier = modifier){
            onEvent(Form6UiEvent.ClearError)}

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
                        Text(text = "Saving step 6...",fontSize = 12.sp,color = Color.White, fontWeight = FontWeight.SemiBold, modifier = modifier.clickable {
                            onEvent(Form6UiEvent.UpdateLoading)
                        })
                    }



                    Text("Cancel", modifier = modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp).clickable {
                        onEvent(Form6UiEvent.UpdateLoading)
                    }, fontWeight = FontWeight.Bold, fontSize = 15.5.sp, fontStyle = FontStyle.Italic)

                }
            }
        }
    }



}