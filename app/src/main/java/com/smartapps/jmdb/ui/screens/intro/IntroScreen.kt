package com.smartapps.jmdb.ui.screens.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smartapps.jmdb.navigation.Routes
import com.smartapps.jmdb.ui.screens.ErrorMessage
import com.smartapps.jmdb.ui.screens.MyTextField
import com.smartapps.jmdb.ui.screens.MyTextField2
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.form5.Form5UiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Intro(modifier: Modifier = Modifier, uiState: StateFlow<IntroUiState>, onEvent:(IntroUiEvent)->Unit, navigate:(String)->Unit) {

    val uiState = uiState.collectAsState()

    if(uiState.value.navigate) {
        onEvent(IntroUiEvent.ClearNavigation)
        navigate(Routes.Form1.route)
    }

    Box {


        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(modifier = modifier.height(450.dp)) {

                Spacer(modifier = modifier.height(20.dp))


                Spacer(modifier = modifier.height(15.dp))
                Spacer(modifier = modifier.height(15.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = modifier.width(15.dp))

                    Text(text = "Registration Type", color = Color.Gray, fontSize = 12.sp)

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


                MyTextField(
                    label = "Tax Identification No. (TIN)",
                    value = uiState.value.tin,
                    onEvent = {
                        onEvent(IntroUiEvent.UpdateTin(it))
                    })
                Spacer(modifier = modifier.height(5.dp))


                MyTextField2(
                    label = "Registration Type",
                    value = uiState.value.registrationType,
                    items = listOf("New Registration", "Continue Registration"),
                    callback = { x, y ->
                        onEvent(IntroUiEvent.UpdateType(y))
                    })
                Spacer(modifier = modifier.height(5.dp))


                Spacer(modifier = modifier.height(15.dp))
                Spacer(modifier = modifier.height(15.dp))




                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(bottom = 20.dp, start = 7.dp, end = 7.dp)
                        .clip(
                            RoundedCornerShape(5.dp)
                        )
                        .background(Color(0xFF66BB6A)).clickable {
                            onEvent(IntroUiEvent.Proceed)
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Start",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }


            }

        }
        ErrorMessage(text = uiState.value.error, modifier = modifier){
            onEvent(IntroUiEvent.ClearError)}


        if(uiState.value.loading) {
            Dialog(onDismissRequest = { }) {
                Box {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF03A9F4)
                        )
                        Spacer(modifier = modifier.height(8.dp))
                        Text(text = "Loading...",fontSize = 12.sp,color = Color.White, fontWeight = FontWeight.SemiBold, modifier = modifier.clickable {
                            onEvent(IntroUiEvent.UpdateLoading)
                        })
                    }
                }
            }
        }

    }



}