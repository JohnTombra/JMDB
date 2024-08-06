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
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.smartapps.jmdb.R
import com.smartapps.jmdb.data.model.CompanyTin
import com.smartapps.jmdb.data.model.IndividualTin
import com.smartapps.jmdb.data.model.MyCompany
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTINActivity2 : AppCompatActivity() {


    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tinactivity2)


        context = this
        val repository = JmdbRepository

        val lga = findViewById<Spinner>(R.id.lga)
        val create = findViewById<TextView>(R.id.done)

        val loadingLayout = findViewById<CardView>(R.id.loadingLayout)
        val completedCard = findViewById<CardView>(R.id.completeCard)

        val companyName = findViewById<EditText>(R.id.companyName)
        val rcNo = findViewById<EditText>(R.id.rcNo)
        val businessIndustry = findViewById<EditText>(R.id.businessIndustry)
        val businessLocation = findViewById<EditText>(R.id.businessLocation)

        val copy = findViewById<TextView>(R.id.copy)

        val tin = findViewById<TextView>(R.id.tin)

        var generatedTin = ""
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        copy.setOnClickListener {
            val clip = ClipData.newPlainText("label", generatedTin)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext,"Copied to Clipboard", Toast.LENGTH_SHORT).show()
        }

        val errorCard = findViewById<CardView>(R.id.errorCard)
        val ok2 = findViewById<TextView>(R.id.ok2)
        val message = findViewById<TextView>(R.id.message)

        ok2.setOnClickListener {
            errorCard.isVisible = false
        }



        findViewById<CardView>(R.id.back).setOnClickListener {
            onBackPressed()
        }


        val ok = findViewById<TextView>(R.id.ok)


        ok.setOnClickListener {
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
        }



        val state = findViewById<EditText>(R.id.state)
        val industry = findViewById<EditText>(R.id.industry)
        val address = findViewById<EditText>(R.id.address)

        val website = findViewById<EditText>(R.id.website)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phoneNumber)



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
                Toast.makeText(context,"Enter a phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(phone.text.length < 11){
                Toast.makeText(context,"Phone number must be 11 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(companyName.text.isEmpty()){
                Toast.makeText(context,"Enter company name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(companyName.text.length < 3){
                Toast.makeText(context,"Company name must be greater than 2 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            if(rcNo.text.isEmpty()){
//                Toast.makeText(context,"Enter rc no", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


            if(businessIndustry.text.isEmpty()){
                Toast.makeText(context,"Enter business industry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(businessIndustry.text.length < 8){
                Toast.makeText(context,"Business industry must be greater than 7 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(lga.selectedItem.toString() == "Select Lga"){
                Toast.makeText(context,"Select lga", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            if(businessLocation.text.toString().isEmpty()){
                Toast.makeText(context,"Enter business location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(businessLocation.text.length < 8){
                Toast.makeText(context,"Business location must be greater than 7 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(industry.text.toString().isEmpty()){
                Toast.makeText(context,"Enter industry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(industry.text.length < 8){
                Toast.makeText(context,"Industry must be greater than 7 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            if(address.text.toString().isEmpty()){
                Toast.makeText(context,"Enter address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(address.text.length < 8){
                Toast.makeText(context,"Address must be greater than 7 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            if(website.text.toString().isEmpty()){
//                Toast.makeText(context,"Enter website url", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


            if(email.text.toString().isEmpty()){
                Toast.makeText(context,"Enter unique email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(!email.text.toString().contains("@")){
                Toast.makeText(context,"Invalid email, please enter a correct and unique email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            AlertDialog.Builder(context).setMessage("Confirm T.I.N Enrollment for (${phone.text.toString()})")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()

                        loadingLayout.isVisible = true


                        val companyTin = MyCompany(
                            company_name = companyName.text.toString(),
                            rc_no = rcNo.text.toString(),
                            business_industry = businessIndustry.text.toString(),
                            business_location = businessLocation.text.toString(),
                            state = state.text.toString(),
                            industry = industry.text.toString(),
                            address = address.text.toString(),
                            website = website.text.toString(),
                            email = email.text.toString(),
                            lga = lga.selectedItem.toString(),
                            phone = phone.text.toString()
                        )

                        repository.createCompanyTin(companyTin,{
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