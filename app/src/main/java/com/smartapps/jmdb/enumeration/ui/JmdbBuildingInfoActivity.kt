package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Base64
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.databinding.ActivityBuildingInfoBinding
import com.smartapps.jmdb.enumeration.data.model.jmdb.Data



class JmdbBuildingInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityBuildingInfoBinding

    data class MyStreet(
        val street_id: String,
        val street: String,
    )

    var str = ""

    lateinit var streets: MutableList<MyStreet>

    lateinit var building: Data

    lateinit var buildingId: String

    lateinit var repository: JmdbRepository

    var id: String? = null

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        context = this

        repository = JmdbRepository


        id = intent.extras!!.getString("id")!!


        binding.ok.setOnClickListener {
            binding.completeCard.isVisible = false
        }


      //  Log.d(TAG, "Building id $id")

        repository.loadBuildingLocally(id!!, { building ->




                binding.sync.setOnClickListener {
                    repository.checkBuildingSyncStatus(building.building_id) {

                        if (it) {


                                AlertDialog.Builder(context)
                                    .setMessage("This building has already been synced")
                                    .setPositiveButton(
                                        "Ok",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                                p0!!.dismiss()
                                            }
                                        }).show()



                        } else {

                                AlertDialog.Builder(context)
                                    .setMessage("This building is ready to be synced")
                                    .setPositiveButton(
                                        "Proceed",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                                //show syncing dialog
                                                binding.syncingLayout.isVisible = true
                                                repository.syncSingle(listOf(building.copy(building_image = building.owner_name, owner_name = "Not set")),
                                                    {
                                                            binding.syncingLayout.isVisible = false
                                                            Toast.makeText(
                                                                context,
                                                                it,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            binding.completeCard.isVisible = true
                                                            binding.status.text = "Error"
                                                            binding.message.text = it
                                                            binding.statusIndicator.setImageResource(
                                                                R.drawable.baseline_close_24
                                                            )

                                                    }) {

                                                        //add synced success dialog
                                                        binding.completeCard.isVisible = true
                                                        binding.syncingLayout.isVisible = false
                                                        binding.status.text = "Success"
                                                        binding.message.text =
                                                            "Successfully synced this building"
                                                        binding.statusIndicator.setImageResource(R.drawable.round_verified_24)

                                                    binding.sync.setOnClickListener {
                                                        Toast.makeText(context,"Already Synced", Toast.LENGTH_SHORT).show()
                                                    }
                                                    repository._100Tagifier()
                                                }

                                            }
                                        }).setNegativeButton(
                                        "Cancel",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                                p0!!.dismiss()
                                            }
                                        }).show()

                        }
                    }
                }


                this@JmdbBuildingInfoActivity.building = building



                binding.loadingLayout.isVisible = false


                buildingId = building.building_id


//                try {
//                    val byte = Base64.decode(
//                        building.building_image.replaceFirst(prefix, ""),
//                        Base64.DEFAULT
//                    )
//                    val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
//              //      binding.photo.setImageBitmap(bitmap)
//
//                    Glide.with(context).load(bitmap).placeholder(R.color.mydrk).centerCrop().fitCenter()
//                        .into(binding.photo)
//
//                } catch (e: Exception) {
//
//                }



                with(building) {


                    binding.lineNo.setText(if(line == "Area line "){"Not set"}else{line})



                    binding.tag.setText(owner_email)

                    binding.approvalCode.setText(if(approval_code.trim().replace(" ","").isEmpty()){"Not set"}else{approval_code})


                    binding.ownerTin.setText(if(tin.isEmpty()){"Not set"}else{tin})
                    binding.buildingNumber.setText(building_number)
                    binding.buildingName.setText(building_name)


                    Glide.with(context).load(Uri.parse(building_image)).placeholder(R.color.mydrk).centerCrop().fitCenter()
                        .into(binding.photo)


                    //get base 64 here
//
//
//                    val inputStream = contentResolver.openInputStream((Uri.parse(building_image)))
//                    val bitmap = BitmapFactory.decodeStream(inputStream)
//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//                    val byteArray = byteArrayOutputStream.toByteArray()
//
//                    str = "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT)
//
//




                    repository.getBuildingTypes(0){
                        try{
                        binding.apartmentType.setText(
                                it.filter { it.idbuilding_types == apartment_type }[0].building_type
                        )}catch(e: Exception){
                            binding.apartmentType.setText("Not set")
                        }
                    }


                    repository.getBuildingCategories{
                        try{
                    binding.apartmentCategory.setText(
                        it.filter { it.idbuilding_category == building_category_id }[0].building_category
                    )}catch(e: Exception){
                            binding.apartmentCategory.setText("Not set")
                    }
                    }




                //    binding.ownerEmail.setText(owner_email.split(",,,")[0])
               //     binding.ownerMobileNumber.setText(owner_mobile_no)
                //    binding.ownerName.setText(owner_name)


                    binding.state.setText(
                        "Jos"
                    )

//                    val strs = repository._getStreetsWithWard()
//                        .filter { it.wardId == state_id.toString() }[0].streets
//
                    try {

//                        repository.getStreets2 {
//                            binding.street.setText(
//                                it.toMutableList().filter { it.idstreet == street_id }[0].street
//                            )
//                        }

                        repository.getStreets2{
                            binding.street.setText(if(street_id == 0){owner_email.split(",,,")[1]}else{it.filter { it.idstreet == street_id }[0].street})
                        }

                    }catch (e:Exception){

                    }

                    repository.getLgas {
                        binding.lga.setText(it.filter { it.lga_id == lga }[0].lga)
                    }

                    repository.getAreas("0") {
                        binding.area.setText(it.filter { it.id == ward }[0].area)
                    }


                    binding.noOfApartments.setText(no_of_apartments)
                    binding.latitude.setText(latitude)
                    binding.longitude.setText(longitude)

                    binding.latitude.setOnClickListener {
                        startActivity(
                            Intent(context, MapActivity::class.java).putExtra(
                                "latitude",
                                latitude.toDouble()
                            ).putExtra("longitude", longitude.toDouble())
                        )
                    }

                    binding.longitude.setOnClickListener {
                        startActivity(
                            Intent(context, MapActivity::class.java).putExtra(
                                "latitude",
                                latitude.toDouble()
                            ).putExtra("longitude", longitude.toDouble())
                        )
                    }
                }


                var items = building.taxitem
                binding.items.setText("")


                   repository.getRevenueItems{ri->

                       items.forEach { id ->
                           try {
                               binding.items.setText(binding.items.text.toString() + "\n${ri.filter { it.assessment_item_id == id }[0].assessment_item_name}")
                           }catch(e:Exception){
                              //   binding.items.setText(binding.items.text.toString() + "\n${ri.filter { it.assessment_item_id == id }[0].assessment_item_name}")
                           }
                       }
                   }






        }) {
                binding.loadingLayout.isVisible = false
                Toast.makeText(this@JmdbBuildingInfoActivity, "Error $it", Toast.LENGTH_SHORT).show()
                onBackPressed()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}