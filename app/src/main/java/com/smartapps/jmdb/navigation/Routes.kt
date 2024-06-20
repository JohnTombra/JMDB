package com.smartapps.jmdb.navigation

sealed class Routes(val route: String){

    object Login: Routes("login")
    object Intro: Routes("intro")
    object Form1: Routes("form1")
    object Form2: Routes("form2")
    object Form3: Routes("form3")
    object Form4: Routes("form4")
    object Form5: Routes("form5")
    object Form6: Routes("form6")


}
