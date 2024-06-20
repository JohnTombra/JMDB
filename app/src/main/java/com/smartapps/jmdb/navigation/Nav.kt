package com.smartapps.jmdb.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.ui.screens.form1.Form1
import com.smartapps.jmdb.ui.screens.form1.Form1ViewModel

import com.smartapps.jmdb.ui.screens.form2.Form2
import com.smartapps.jmdb.ui.screens.form2.Form2ViewModel

import com.smartapps.jmdb.ui.screens.form3.Form3
import com.smartapps.jmdb.ui.screens.form3.Form3ViewModel

import com.smartapps.jmdb.ui.screens.form4.Form4
import com.smartapps.jmdb.ui.screens.form4.Form4ViewModel

import com.smartapps.jmdb.ui.screens.form5.Form5
import com.smartapps.jmdb.ui.screens.form5.Form5ViewModel

import com.smartapps.jmdb.ui.screens.form6.Form6
import com.smartapps.jmdb.ui.screens.form6.Form6ViewModel

import com.smartapps.jmdb.ui.screens.intro.Intro
import com.smartapps.jmdb.ui.screens.intro.IntroViewModel
import com.smartapps.jmdb.ui.screens.login.LoginScreen
import com.smartapps.jmdb.ui.screens.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(repository: JmdbRepository, selectPdf: (Int)-> Unit, congrats: ()->Unit) {


//    val repository = Repository()
//    val viewModel =


    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Routes.Form1.route){

        composable(route = Routes.Login.route){

            val viewModel = LoginViewModel(repository)

            LoginScreen(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){
                    Routes.Intro.route -> {
                        navController.navigate(Routes.Intro.route)
                    }
                }
            }
        }

        composable(route = Routes.Intro.route){
            val viewModel = IntroViewModel(repository)

            Intro(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){

                    Routes.Form1.route ->{
                        navController.navigate(Routes.Form1.route)
                    }
                }
            }
        }

        composable(route = Routes.Form1.route){
            val viewModel =
                Form1ViewModel(repository)

            Form1(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){
                    Routes.Form2.route ->{
                        navController.navigate(Routes.Form2.route)
                    }
                }
            }
        }

        composable(route = Routes.Form2.route){
            val viewModel =
                Form2ViewModel(repository)

            Form2(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){
                    Routes.Form3.route -> {
                        navController.navigate(Routes.Form3.route)
                    }
                }
            }
        }

        composable(route = Routes.Form3.route){
            val viewModel =
                Form3ViewModel(repository)

            Form3(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
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
            val viewModel =
                Form4ViewModel(repository)

            Form4(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){
                    Routes.Form5.route -> {
                        navController.navigate(Routes.Form5.route)
                    }
                }
            }
        }

        composable(route = Routes.Form5.route){
            val viewModel =
                Form5ViewModel(repository)

            Form5(uiState = viewModel.uiState, onEvent = viewModel::onEvent){
                when(it){
                    Routes.Form6.route -> {
                        navController.navigate(Routes.Form6.route)
                    }
                }
            }
        }


        composable(route = Routes.Form6.route){
            val viewModel =
                Form6ViewModel(repository)

            Form6(uiState = viewModel.uiState, onEvent = viewModel::onEvent, select = {
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