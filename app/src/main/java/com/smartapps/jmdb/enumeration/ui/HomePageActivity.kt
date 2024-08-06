package com.smartapps.jmdb.enumeration.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible

import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val context = this
        val repository = JmdbRepository


        binding.two.setOnClickListener {
            startActivity(Intent(this, TINIntermediate::class.java))
        }


        binding.ok.setOnClickListener {
            binding.completeCard.isVisible = false
        }

        binding.synchronize.setOnClickListener {


            AlertDialog.Builder(context).setMessage("Are you sure you want to perform synchronization")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                        binding.loadingScreen.isVisible = true



                            repository.initializeData({
                                binding.progressText.text = "Synchronizing meta data...\n$it"
                            },{
                                    binding.loadingScreen.isVisible = false
                                    binding.completeCard.isVisible = true
                            }){
                                    Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                            }


                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }
                }).show()






        }

        binding.buildings.setOnClickListener {
            SECTION = "CREATE"
            startActivity(Intent(this, TIN_PAGE::class.java))
        }


        binding.one.setOnClickListener {
            startActivity(Intent(this, RegistrationIntermediate::class.java))
        }


        binding.business.setOnClickListener {
            startActivity(Intent(this, JmdbBuildingsListActivity::class.java))
        }



        binding.three.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }



        binding.logout.setOnClickListener {
            startActivity(Intent(this, AUTHENTICATION_LOGIN::class.java))
            finishAffinity()
        }



    }

    override fun onBackPressed() {
        super.moveTaskToBack(true)
    }


}