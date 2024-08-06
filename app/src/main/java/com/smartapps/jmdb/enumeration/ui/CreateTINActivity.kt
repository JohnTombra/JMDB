package com.smartapps.jmdb.enumeration.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.smartapps.jmdb.R
import com.smartapps.jmdb.data.model.IndividualTin
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTINActivity : AppCompatActivity() {

    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tinactivity)


        context = this

        val gender = findViewById<Spinner>(R.id.gender)
        val lga = findViewById<Spinner>(R.id.lga)
        val create = findViewById<TextView>(R.id.done)

        val loadingLayout = findViewById<CardView>(R.id.loadingLayout)
        val completedCard = findViewById<CardView>(R.id.completeCard)

        val errorCard = findViewById<CardView>(R.id.errorCard)
        val ok2 = findViewById<TextView>(R.id.ok2)
        val message = findViewById<TextView>(R.id.message)

        val copy = findViewById<TextView>(R.id.copy)

        val tin = findViewById<TextView>(R.id.tin)

        var generatedTin = ""
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        copy.setOnClickListener {
            val clip = ClipData.newPlainText("label", generatedTin)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext,"Copied to Clipboard", Toast.LENGTH_SHORT).show()
        }


        ok2.setOnClickListener {
            errorCard.isVisible = false
        }



        var x = 0

        val typeLgaCard = findViewById<LinearLayoutCompat>(R.id.typeLgaCard)
        val typeLga= findViewById<TextView>(R.id.typeLga)


        val lgaSelect = findViewById<CardView>(R.id.lgaSelect)
        val lgaType = findViewById<CardView>(R.id.lgaType)

        val lgaTyped = findViewById<EditText>(R.id.lgaTyped)


        typeLgaCard.setOnClickListener {

            if(x == 0) {
               lgaSelect.isVisible = false
                lgaType.isVisible = true
                typeLga.setText("Select")
                x = 1
            }else{
                lgaSelect.isVisible = true
                lgaType.isVisible = false
                typeLga.setText("Type")
                x = 0
            }


        }

        findViewById<CardView>(R.id.back).setOnClickListener {
            onBackPressed()
        }




        val ok = findViewById<TextView>(R.id.ok)


        ok.setOnClickListener {
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
        }


        val firstName = findViewById<EditText>(R.id.firstName)
        val surName = findViewById<EditText>(R.id.surname)
        val otherNames = findViewById<EditText>(R.id.otherNames)
        val homeTown = findViewById<EditText>(R.id.homeTown)
        val nationality = findViewById<EditText>(R.id.nationality)
        val maritalStatus = findViewById<Spinner>(R.id.mStatus)
        val soo = findViewById<EditText>(R.id.soo)
        val payerAddress = findViewById<EditText>(R.id.payerAddress)

        val year = findViewById<EditText>(R.id.year)
        val month = findViewById<EditText>(R.id.month)
        val day = findViewById<EditText>(R.id.day)
        val occupation = findViewById<EditText>(R.id.occupation)
        val phone = findViewById<EditText>(R.id.phoneNumber)
        val email = findViewById<EditText>(R.id.email)





        val repository = JmdbRepository


        val genderAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item,
            mutableListOf("Select Gender","Male","Female")
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender.adapter = genderAdapter



        val maritalStatusAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item,
            mutableListOf("Select Marital Status","Single","Married","Divorced", "Other")
        )
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maritalStatus.adapter = maritalStatusAdapter



        var lgas: MutableList<Lga> = mutableListOf()

             repository.getLgas{
                 lgas = it.toMutableList()

                 val lgaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                     context,
                     R.layout.spinner_item,
                     mutableListOf("Select Lga") + lgas.map { it.lga }
                 )
                 lgaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                 lga.adapter = lgaAdapter

             }


        create.setOnClickListener {

            if(phone.text.isEmpty()){
                Toast.makeText(context,"Enter phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(phone.text.length < 11){
                Toast.makeText(context,"Phone number must be 11 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(firstName.text.isEmpty()){
                Toast.makeText(context,"Enter first name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(firstName.text.length < 2){
                Toast.makeText(context,"First name must be greater than 1 character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(surName.text.isEmpty()){
                Toast.makeText(context,"Enter surname", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(surName.text.length < 2){
                Toast.makeText(context,"Surname must be greater than 1 character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            if(otherNames.text.isEmpty()){
//                Toast.makeText(context,"Enter first name", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


            if(homeTown.text.isEmpty()){
                Toast.makeText(context,"Enter home town", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(homeTown.text.length < 2){
                Toast.makeText(context,"Home town must be greater than 1 character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(soo.text.isEmpty()){
                Toast.makeText(context,"Enter state of origin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(x == 1){

                if(lgaTyped.text.isEmpty()) {
                    Toast.makeText(context, "Enter Lga", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(lgaTyped.text.length < 2) {
                    Toast.makeText(context, "Lga length must be greater than 1 character", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            }else{
                if(lga.selectedItem.toString() == "Select Lga") {
                    Toast.makeText(context, "Select Lga", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if(payerAddress.text.isEmpty()){
                Toast.makeText(context,"Enter address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(payerAddress.text.length < 2){
                Toast.makeText(context,"Payer address must be greater than 1 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            if(gender.selectedItem.toString() == "Select Gender"){
                Toast.makeText(context,"Select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(maritalStatus.selectedItem.toString() == "Select Marital Status"){
                Toast.makeText(context,"Select marital status", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


//            if(soo.text.isEmpty()){
//                Toast.makeText(context,"Enter S.O.O", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if(soo.text.length < 3){
//                Toast.makeText(context,"S.O.O must be greater than 2 characters", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }





          //  if(lga.selectedItem.toString() == "Select Lga"){
//                Toast.makeText(context,"Select lga", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener


         //   }






            if(year.text.isEmpty()){
                Toast.makeText(context,"Enter year of birth", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(month.text.isEmpty()){
                Toast.makeText(context,"Enter month of birth", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(day.text.isEmpty()){
                Toast.makeText(context,"Enter day of birth", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(occupation.text.isEmpty()){
                Toast.makeText(context,"Enter occupation", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(occupation.text.length < 9){
                Toast.makeText(context,"Occupation must be greater than 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(email.text.isEmpty()){
                Toast.makeText(context,"Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(email.text.length < 15){
                Toast.makeText(context,"Email length too short", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            AlertDialog.Builder(context).setMessage("Confirm T.I.N Enrollment for (${phone.text.toString()})")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()


                        loadingLayout.isVisible = true


                val Day = day.text.toString().toInt()
              val  Month = month.text.toString().toInt() + 1
               val Year = year.text.toString().toInt()

              val date =  if(Day.toString().length < 2){"0"+Day.toString()}else{Day.toString()} + "-" + if(Month.toString().length < 2){"0" + Month.toString()}else{Month.toString()} + "-" + Year.toString()



                        val individualTin = IndividualTin(
                            first_name = firstName.text.toString(),
                            surname = surName.text.toString(),
                            other_names = otherNames.text.toString(),
                            home_town = homeTown.text.toString(),
                            nationality = nationality.text.toString(),
                            gender = gender.selectedItem.toString(),
                            marital_status = maritalStatus.selectedItem.toString(),
                            soo = soo.text.toString(),
                            payer_address = payerAddress.text.toString(),
                            lga = if(x == 1){lgaTyped.text.toString()}else{lga.selectedItem.toString()},
                            dob = date,
                            occupation = occupation.text.toString(),
                            phone = phone.text.toString(),
                            email = email.text.toString()
                        )

                        repository.createIndividualTin(individualTin,{
                            generatedTin = it
                            tin.text = "[ " + it + " ]"
                            loadingLayout.isVisible = false
                            completedCard.isVisible = true
                        }){

                            message.text = if(it.contains("unique value")) {

                                if (it.contains("Phone number") && it.contains("Email")) {
                                    "Phone number has already been used, Email has already been used"
                                } else if (it.contains("Phone number")) {
                                    "Phone number has already been used"
                                } else if (it.contains("Email")) {
                                    "Email has already been used"
                                }else{
                                    it
                                }
                            }else{
                                it
                            }

                            loadingLayout.isVisible = false
                            errorCard.isVisible = true
                        }



                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }
                }).show()




            //connect to repository

        }




    }

    override fun onBackPressed() {

        fun bp() {
            super.onBackPressed()
        }

        AlertDialog.Builder(context).setMessage("Are you sure you want exit")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    bp()
                    p0!!.dismiss()
                }
            }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0!!.dismiss()
                }
            }).show()
    }


}