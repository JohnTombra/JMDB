package com.smartapps.jmdb

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import com.novaapps.jmdb.data.repository.Repository
import com.smartapps.jmdb.enumeration.authentication.CONGRATS_PAGE
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.ui.prefix
import com.smartapps.jmdb.navigation.Nav
import com.smartapps.jmdb.ui.theme.JMDBTheme
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {

    lateinit var repository: JmdbRepository

    private val PICK_PDF_REQUEST = 2
    var cursor = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       repository = JmdbRepository(this)
        setContent {
            JMDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Nav(repository,{
                       cursor = it
                       openPdfChooser()
                   }){
                       startActivity(Intent(this, CONGRATS_PAGE::class.java))
                       finish()
                   }
                }
            }
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.data != null) {
            val dt = data.data

            var image = "HELLO"


            val uri: Uri = data.data!!

            try {

                contentResolver.openInputStream(uri!!)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    image = "data:application/pdf;base64," + Base64.encodeToString(bytes, Base64.DEFAULT)
                    Log.d("PDFTIME", "error: ${image}")
                }
            } catch (e: Exception) {
                Log.d("PDFTIME", "error: ${e.message}")
                e.printStackTrace()
            }







            when (cursor) {
                1 -> {

                    JmdbRepository.IMAGE_1 = image
                    //    repository.uploadPdf("Copy of the certificate of occupancy",image)
                }

                2 -> {
                    JmdbRepository.IMAGE_2 = image
                    //    repository.uploadPdf("Copy of MLS TP Acknowledge Letter",image)
                }

                3 -> {
                    JmdbRepository.IMAGE_3 = image
                    //     repository.uploadPdf("Copy of Structural Drawing and Details",image)
                }

                4 -> {
                    JmdbRepository.IMAGE_4 = image
                    //      repository.uploadPdf("Copy of Structural Calculations",image)
                }

                5 -> {
                    JmdbRepository.IMAGE_5 = image
                    //     repository.uploadPdf("Copy of Architectural Drawing and Details",image)
                }

                6 -> {
                    JmdbRepository.IMAGE_6 = image
                    //      repository.uploadPdf("Copy of Mechanical Electrical Drawings and Details",image)
                }

                7 -> {
                    JmdbRepository.IMAGE_7 = image
                    //   repository.uploadPdf("Site Analysis Report",image)
                }

                8 -> {
                    JmdbRepository.IMAGE_8 = image
                    //  repository.uploadPdf("Copy of EIAR",image)
                }

                9 -> {
                    JmdbRepository.IMAGE_9 = image
                    //   repository.uploadPdf("Copy of BLOCK Plan",image)
                }

                10 -> {
                    JmdbRepository.IMAGE_10 = image
                    //       repository.uploadPdf("Soil Investigation Report",image)
                }

                11 -> {
                    JmdbRepository.IMAGE_11 = image
                    //     repository.uploadPdf("Copy of Service Approvals",image)
                }
            }
        }
        }


    private fun openPdfChooser() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_PDF_REQUEST)
    }

}










@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JMDBTheme {
        Greeting("Android")
    }
}