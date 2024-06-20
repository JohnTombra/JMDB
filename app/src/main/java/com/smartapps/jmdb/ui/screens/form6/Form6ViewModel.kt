package com.smartapps.jmdb.ui.screens.form6

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.ui.screens.form1.Form1UiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiEvent
import com.smartapps.jmdb.ui.screens.login.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Form6ViewModel(
    val repository: JmdbRepository
): ViewModel()  {


    lateinit var timer1: CountDownTimer
    lateinit var timer2: CountDownTimer
    lateinit var timer3: CountDownTimer
    lateinit var timer4: CountDownTimer
    lateinit var timer5: CountDownTimer
    lateinit var timer6: CountDownTimer
    lateinit var timer7: CountDownTimer
    lateinit var timer8: CountDownTimer
    lateinit var timer9: CountDownTimer
    lateinit var timer10: CountDownTimer
    lateinit var timer11: CountDownTimer




    init {
        listenFor1()
        listenFor2()
        listenFor3()
        listenFor4()
        listenFor5()
        listenFor6()
        listenFor7()
        listenFor8()
        listenFor9()
        listenFor10()
        listenFor11()
    }



    private val _uiState = MutableStateFlow(Form6UiState())
    val uiState: StateFlow<Form6UiState> = _uiState



    fun onEvent(event: Form6UiEvent){

        when(event){
            Form6UiEvent.ClearError -> {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    _uiState.update { it.copy(error = null) }
                }
            }

            Form6UiEvent.Proceed -> {

                if(JmdbRepository.IMAGE_1.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_2.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_3.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_4.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_5.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_6.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_7.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_8.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_9.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_10.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                if(JmdbRepository.IMAGE_11.isEmpty()){
                    _uiState.update { it.copy(error = "All documents are required") }
                    return
                }

                _uiState.update { it.copy(loading = true) }


                repository.uploadDocuments({
                    if(_uiState.value.loading) {
                       _uiState.update { it.copy(navigate = true) } }

                }){error ->
                    if(_uiState.value.loading) {
                    _uiState.update { it.copy(loading = false, error = error) } }
                }



            }
            is Form6UiEvent.UpdateBase64 -> {
                _uiState.update { it.copy(base64 = event.value) }
            }
            Form6UiEvent.UpdateLoading -> {
                _uiState.update { it.copy(loading = false) }
            }

            Form6UiEvent.ClearNavigation -> {
                _uiState.update { it.copy(navigate = false) }
            }
        }

    }



    fun listenFor1(){

      timer1 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                _uiState.update { it.copy(image1 = JmdbRepository.IMAGE_1) }
                timer1.start()
            }

        }

        timer1.start()

    }

    fun listenFor2(){
        timer2 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image2 = JmdbRepository.IMAGE_2) }
                timer2.start()
            }

        }

        timer2.start()
    }

    fun listenFor3(){
        timer3 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image3 = JmdbRepository.IMAGE_3) }
                timer3.start()
            }

        }

        timer3.start()
    }

    fun listenFor4(){
        timer4 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image4 = JmdbRepository.IMAGE_4) }
                timer4.start()
            }

        }

        timer4.start()
    }

    fun listenFor5(){
        timer5 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image5 = JmdbRepository.IMAGE_5) }
                timer5.start()
            }

        }

        timer5.start()
    }

    fun listenFor6(){
        timer6 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image6 = JmdbRepository.IMAGE_6) }
                timer6.start()
            }

        }

        timer6.start()
    }

    fun listenFor7(){
        timer7 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image7 = JmdbRepository.IMAGE_7) }
                timer7.start()
            }

        }

        timer7.start()
    }

    fun listenFor8(){
        timer8 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image8 = JmdbRepository.IMAGE_8) }
                timer8.start()
            }

        }

        timer8.start()
    }

    fun listenFor9(){
        timer9 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image9 = JmdbRepository.IMAGE_9) }
                timer9.start()
            }

        }

        timer9.start()
    }

    fun listenFor10(){
        timer10 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image10 = JmdbRepository.IMAGE_10) }
                timer10.start()
            }

        }

        timer10.start()
    }

    fun listenFor11(){
        timer11 =  object: CountDownTimer(500,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                _uiState.update { it.copy(image11 = JmdbRepository.IMAGE_11) }
                timer11.start()
            }

        }

        timer11.start()
    }









}