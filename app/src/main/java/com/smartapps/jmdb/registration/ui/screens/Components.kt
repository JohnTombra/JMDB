package com.smartapps.jmdb.registration.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Done

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartapps.jmdb.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 1500)
@Composable
fun ScreenA(modifier: Modifier = Modifier) {



    Column(){

        Spacer(modifier = modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = modifier.width(15.dp))

            Image(painter = painterResource(id = R.drawable.baseline_view_compact_24), contentDescription = null, modifier =  modifier.size(18.dp))

            Spacer(modifier = modifier.width(10.dp))
            Box(modifier = modifier
                .background(Color.LightGray)
                .height(1.dp)
                .weight(1f))
            Spacer(modifier = modifier.width(10.dp))
        }

        Spacer(modifier = modifier.height(15.dp))

        MyTextField(label = "Tax Identification No. (TIN)",value = "")
        Spacer(modifier = modifier.height(5.dp))

        MyTextField(label = "Phone 1",value = "")
        Spacer(modifier = modifier.height(5.dp))

        MyTextField(label = "Phone 2",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField(label = "Email Address",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField(label = "ID Number",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField(label = "Occupation",value = "")
        Spacer(modifier = modifier.height(20.dp))


        Row(verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = modifier.width(15.dp))
            Image(painter = painterResource(id = R.drawable.baseline_view_compact_24), contentDescription = null, modifier =  modifier.size(18.dp))
            Spacer(modifier = modifier.width(10.dp))
            Box(modifier = modifier
                .background(Color.LightGray)
                .height(1.dp)
                .weight(1f))
            Spacer(modifier = modifier.width(10.dp))
        }




        Spacer(modifier = modifier.height(15.dp))

        MyTextField3(label = "Date of birth",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField2(label = "Nationality",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField2(label = "State",value = "")
        Spacer(modifier = modifier.height(5.dp))

        MyTextField2(label = "LGA",value = "")
        Spacer(modifier = modifier.height(5.dp))


        MyTextField2(label = "Identification",value = "")

        Spacer(modifier = modifier.height(15.dp))
        Spacer(modifier = modifier.height(15.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = modifier.width(15.dp))

            Text(text = "Gender", color = Color.Gray, fontSize = 12.sp)

            Spacer(modifier = modifier.width(10.dp))
            Box(modifier = modifier
                .background(Color.LightGray)
                .height(1.dp)
                .weight(1f))
            Spacer(modifier = modifier.width(10.dp))
        }


        Spacer(modifier = modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = false, onClick = { })
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Male", color = Color.Gray, fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = false, onClick = { })
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Female", color = Color.Gray, fontSize = 12.sp)
            }


        }





        Spacer(modifier = modifier.height(10.dp))
        Spacer(modifier = modifier.height(15.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = modifier.width(15.dp))

            Text(text = "Application Type", color = Color.Gray, fontSize = 12.sp)

            Spacer(modifier = modifier.width(10.dp))
            Box(modifier = modifier
                .background(Color.LightGray)
                .height(1.dp)
                .weight(1f))
            Spacer(modifier = modifier.width(10.dp))
        }


        Spacer(modifier = modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "New Proposal", color = Color.Gray, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Renovation", color = Color.Gray, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "As-Built", color = Color.Gray, fontSize = 12.sp)
            }
        }





        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Remodeling", color = Color.Gray, fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Regularization", color = Color.Gray, fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(120.dp)) {
                Checkbox(checked = false, onCheckedChange =  { }, modifier = modifier.width(40.dp))
                Spacer(modifier = modifier.width(0.dp))
                Text(text = "Other", color = Color.Gray, fontSize = 12.sp)
            }
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
                .background(Color(0xFFEF5350)), contentAlignment = Alignment.Center
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


    
}


@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, heightDp = 600)
@Composable
fun MyTextField(modifier: Modifier = Modifier, label:String,value:String, onEvent: (String)->Unit= {x->}) {

    val textColorLight2 = Color(0xFF555555)

    Box(modifier = modifier.padding(top = 5.dp)) {

     //   Box(modifier = modifier.fillMaxWidth().height(95.dp).background(Color.White).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp).border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Gray)) {



        OutlinedTextField(
            textStyle = TextStyle.Default.copy(fontSize = 13.sp),
            value = value,
            onValueChange = {
                            onEvent(it)
            },

            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor  = Color.LightGray,
                unfocusedIndicatorColor  = Color.Gray,
                focusedIndicatorColor = Color(0xFF4CAF50),
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .height(60.dp)
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .background(Color.White),
            label = {
                Text(text = label, fontSize = 12.sp, color = textColorLight2)
            }
        )


        }



 //   }



}




@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, heightDp = 600)
@Composable
fun MyTextFieldNum(modifier: Modifier = Modifier, label:String,value:String, onEvent: (String)->Unit= {x->}, maxLength: Int = 10000) {

    val textColorLight2 = Color(0xFF555555)

    Box(modifier = modifier.padding(top = 5.dp)) {

        //   Box(modifier = modifier.fillMaxWidth().height(95.dp).background(Color.White).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp).border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Gray)) {



        OutlinedTextField(
            textStyle = TextStyle.Default.copy(fontSize = 13.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            value = value,
            onValueChange = {


                if(it.length <= maxLength){
                onEvent(it)
                }


            },

            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor  = Color.LightGray,
                unfocusedIndicatorColor  = Color.Gray,
                focusedIndicatorColor = Color(0xFF4CAF50),
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .height(60.dp)
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .background(Color.White),
            label = {
                Text(text = label, fontSize = 12.sp, color = textColorLight2)
            }
        )


    }



    //   }



}




@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, heightDp = 600)
@Composable
fun MyTextFieldDark(modifier: Modifier = Modifier, label:String,value:String, onEvent: (String)->Unit= {x->}) {

    val textColorLight2 = Color(0xFF555555)

    Box(modifier = modifier.padding(top = 5.dp)) {

        //   Box(modifier = modifier.fillMaxWidth().height(95.dp).background(Color.White).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp).border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Gray)) {



        OutlinedTextField(
            value = value,
            onValueChange = {
                onEvent(it)
            },
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor  = Color.Transparent,
                unfocusedIndicatorColor  = Color(0xFFEEEEEE),
                focusedIndicatorColor = Color(0xFF4CAF50),
                containerColor = Color(0xFFF2F2F2),
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .height(60.dp)
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
            ,
            label = {
                Text(text = label, fontSize = 10.sp, color = textColorLight2)
            }
        )


    }



    //   }



}





@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, heightDp = 600)
@Composable
fun MyTextField2(modifier: Modifier = Modifier, label:String,value:String,items: List<String> = listOf(), callback: (Int, String) -> Unit= {x,y->}) {

    val textColorLight2 = Color(0xFF555555)


    var isExpanded by remember {
        mutableStateOf(false)
    }

    var text by remember {
        mutableStateOf("")
    }

    var focused by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }, modifier = modifier.fillMaxWidth().padding(top = 5.dp)
    ) {


    Box() {

        //   Box(modifier = modifier.fillMaxWidth().height(95.dp).background(Color.White).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp).border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Gray)) {



        OutlinedTextField(
            textStyle = TextStyle.Default.copy(fontSize = 13.sp, color = Color.DarkGray),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),
            readOnly = true,
            enabled = false,
            value = value,
            onValueChange = {

            },

            trailingIcon = {
                 Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF4CAF50), modifier =  modifier.size(22.dp))
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor  = Color.LightGray,
                unfocusedIndicatorColor  = Color.Gray,
                focusedIndicatorColor = Color(0xFF4CAF50),
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .height(60.dp)
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    isExpanded = true
                        }.menuAnchor(),
            label = {
                Text(text = label, fontSize = 12.sp, color = textColorLight2)
            }
        )


    }




        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
            modifier =
            modifier
                .fillMaxWidth(0.8f).padding(start = 10.dp)
                .background(Color(0xFFF5F5F5))
        ) {

            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(text = s, fontWeight = FontWeight.Bold)
                    },
                    onClick = {
                        text = s
                        isExpanded = false
                        callback(index, s)
                    }
                )
            }

        }


    }




}






@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, heightDp = 600)
@Composable
fun MyTextField3(modifier: Modifier = Modifier, label:String,value:String, onChange: (String)->Unit = {},onClick: () -> Unit = {}) {

    val textColorLight2 = Color(0xFF555555)

    Box(modifier = modifier.clickable { 
        onClick()
    }) {

        //   Box(modifier = modifier.fillMaxWidth().height(95.dp).background(Color.White).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp).border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Gray)) {




        

        OutlinedTextField(
            textStyle = TextStyle.Default.copy(fontSize = 13.sp, color = Color.DarkGray),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),
            readOnly = true,
            value = value,
            enabled = false,
            onValueChange = {
                            onChange(it)
            },

            trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF4CAF50), modifier = modifier
                        .size(20.dp))
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor  = Color.LightGray,
                unfocusedIndicatorColor  = Color.Gray,
                focusedIndicatorColor = Color(0xFF4CAF50),
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .height(60.dp)
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .background(Color.White)
                .onFocusChanged {  x ->
                                if(x.isFocused){
                                    onClick()
                                }
                },
            label = {
                Text(text = label, fontSize = 12.sp, color = textColorLight2)
            }
        )


        Box(modifier = modifier.padding(start = 15.dp, end = 10.dp, top = 5.dp).background(Color.Transparent).height(55.dp).fillMaxWidth().clickable {
            onClick()
        }){

        }

    }






}


val errorColor = Color(0xFFFC6C85)
@Composable
fun ErrorMessage(modifier: Modifier = Modifier, text: String?, clear: ()->Unit = {}) {


    AnimatedVisibility(
        visible = text != null,
        enter = slideInVertically(
            initialOffsetY = {-it}
        ) + EnterTransition.None,
        exit = slideOutVertically(
            targetOffsetY = {-it}
        ) + ExitTransition.None
    ){

        Box(modifier = modifier.fillMaxWidth().height(115.dp).padding(start = 13.dp, end = 13.dp, top = 26.dp).clip(
            RoundedCornerShape(15.dp)
        ).background(
            errorColor //Color(0xFF46923C)
        )){


//
//            Icon(
//                imageVector = Icons.Outlined.Done,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = modifier
//                    .size(95.dp).align(Alignment.CenterStart).alpha(0.6f)
//            )
//


            Text(text ?: "",fontSize = 13.sp,fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center, modifier = modifier.align(Alignment.Center))






        }

    }
    if(text!=null){
        clear()}
}

