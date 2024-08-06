package com.smartapps.jmdb.registration.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.registration.ui.screens.form1.Form1
import com.smartapps.jmdb.registration.ui.screens.form1.Form1ViewModel
import com.smartapps.jmdb.registration.ui.screens.form2.Form2
import com.smartapps.jmdb.registration.ui.screens.form2.Form2ViewModel
import com.smartapps.jmdb.registration.ui.screens.form3.Form3
import com.smartapps.jmdb.registration.ui.screens.form3.Form3ViewModel
import com.smartapps.jmdb.registration.ui.screens.form4.Form4
import com.smartapps.jmdb.registration.ui.screens.form4.Form4ViewModel
import com.smartapps.jmdb.registration.ui.screens.form5.Form5
import com.smartapps.jmdb.registration.ui.screens.form5.Form5ViewModel
import com.smartapps.jmdb.registration.ui.screens.form6.Form6
import com.smartapps.jmdb.registration.ui.screens.form6.Form6ViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(repository: JmdbRepository, selectPdf: (Int)-> Unit, congrats: ()->Unit) {


//    val repository = Repository()
//    val viewModel =


    val navController = rememberNavController()
    val form1ViewModel =
        Form1ViewModel(repository)

    val form2ViewModel =
        Form2ViewModel(repository)


    val form3ViewModel =
        Form3ViewModel(repository)

    val form4ViewModel =
        Form4ViewModel(repository)


    val form5ViewModel =
        Form5ViewModel(repository)

    val form6ViewModel =
        Form6ViewModel(repository)


    NavHost(navController = navController, startDestination = Routes.Form1.route){

        composable(route = Routes.Login.route){


        }

        composable(route = Routes.Intro.route){

        }

        composable(route = Routes.Form1.route){


            Form1(uiState = form1ViewModel.uiState, onEvent = form1ViewModel::onEvent){
                when(it){
                    Routes.Form2.route ->{
                        navController.navigate(Routes.Form2.route)
                    }
                }
            }
        }

        composable(route = Routes.Form2.route){


            Form2(uiState = form2ViewModel.uiState, onEvent = form2ViewModel::onEvent){
                when(it){
                    Routes.Form3.route -> {
                        navController.navigate(Routes.Form3.route)
                    }
                }
            }
        }

        composable(route = Routes.Form3.route){


            Form3(uiState = form3ViewModel.uiState, onEvent = form3ViewModel::onEvent){
                when(it){
                    Routes.Form4.route -> {
                        navController.navigate(Routes.Form4.route)
                    }
                    Routes.Form5.route -> {
                        navController.navigate(Routes.Form5.route)
                    }
                }
            }
        }

        composable(route = Routes.Form4.route){


            Form4(uiState = form4ViewModel.uiState, onEvent = form4ViewModel::onEvent){
                when(it){
                    Routes.Form5.route -> {
                        navController.navigate(Routes.Form5.route)
                    }
                }
            }
        }

        composable(route = Routes.Form5.route){


            Form5(uiState = form5ViewModel.uiState, onEvent = form5ViewModel::onEvent){
                when(it){
                    Routes.Form6.route -> {
                        navController.navigate(Routes.Form6.route)
                    }
                }
            }
        }


        composable(route = Routes.Form6.route){

            Form6(uiState = form6ViewModel.uiState, onEvent = form6ViewModel::onEvent, select = {
                selectPdf(it)
            }, navigate = {
                when(it){

                }
            }){
                congrats()
            }
        }



    }


}