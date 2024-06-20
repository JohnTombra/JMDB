package com.smartapps.jmdb.enumeration.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.location.LocationServices
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.OWNER_EMAIL
import com.smartapps.jmdb.enumeration.data.repository.OWNER_NAME
import com.smartapps.jmdb.enumeration.data.repository.OWNER_NUMBER
import com.smartapps.jmdb.enumeration.data.repository.TIN
import com.smartapps.jmdb.databinding.ActivityNewBuilding2Binding
import com.smartapps.jmdb.enumeration.model.jmdb.AssessmentItem
import com.smartapps.jmdb.enumeration.model.jmdb.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val prefix = "data:image/jpeg;base64,"


class JmdbNewBuildingActivity : AppCompatActivity() {


    var image = 0
    private val REQUEST_IMAGE_CAPTURE = 1211


    private var mImageUri1: String? = null
    private var mImageUri2: Uri? = null

    private var mUri: Uri? = null


    lateinit var binding: ActivityNewBuilding2Binding
    private val pic_id = 123


    data class MyStreet(
        val street_id: String,
        val street: String,
    )



    var call = false


    var myBitmap: Bitmap? = null

    var forceClose = false

    var taxPayersName = ""

    var buildingId: String? = null


    lateinit var repository: JmdbRepository


    private val permissionCode = 101

  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var context: Context
//
    lateinit var locationRequest: LocationRequest


    lateinit var displayable: MutableList<com.smartapps.jmdb.data.model.Result>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBuilding2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        repository = JmdbRepository(this)

        context = this


        binding.back.setOnClickListener {
            onBackPressed()
        }



        binding.ok.setOnClickListener {
            startActivity(Intent(context, HomePageActivity::class.java))
            /*      .addFlags(
                  Intent.FLAG_ACTIVITY_NEW_TASK
              ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))*/
            finish()
        }



        binding.addImage.setOnClickListener {
            binding.addImage.isVisible = false
            dispatchTakePictureIntent()
        }

        x()



        binding.ownerName.setText(OWNER_NAME)
        binding.ownerEmail.setText(OWNER_EMAIL)
        binding.ownerMobileNumber.setText(OWNER_NUMBER)
//        binding.modalPhoneNumber.setText(OWNER_NUMBER)



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest =
            LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(100)
                .setFastestInterval(100)


        fetchLocation { d, d2 -> }

        object: CountDownTimer(4000,1000){

            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                fetchLocation { d, d2 -> }
            }


        }.start()

        binding.capture1.setOnClickListener {
            call = true
            binding.latitude.setHint("Fetching...")
            binding.longitude.setHint("Fetching...")
            fetchLocation { X, Y ->
                binding.latitude.setText(X.toString())
                binding.longitude.setText(Y.toString())
            }
        }

        binding.capture2.setOnClickListener {
            call = true
            binding.latitude.setHint("Fetching...")
            binding.longitude.setHint("Fetching...")
            fetchLocation { X, Y ->
                binding.latitude.setText(X.toString())
                binding.longitude.setText(Y.toString())
            }
        }



        var apartmentCategories: MutableList<com.smartapps.jmdb.enumeration.model.jmdb.BuildingCategory> = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            apartmentCategories = repository.getBuildingCategories().toMutableList()


            val apartmentCategoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                R.layout.spinner_item,
                mutableListOf("Select Building Category") + apartmentCategories.map { it.building_category }
            )
            apartmentCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.apartmentCategory.adapter = apartmentCategoriesAdapter
        }



        var apartmentTypes: MutableList<com.smartapps.jmdb.enumeration.model.jmdb.BuildingType> = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            apartmentTypes = repository.getBuildingTypes().toMutableList()


            val apartmentTypeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                R.layout.spinner_item,
                mutableListOf("Select Building Type") + apartmentTypes.map { it.building_type }
            )
            apartmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.apartmentType.adapter = apartmentTypeAdapter
        }



        var lgas: MutableList<com.smartapps.jmdb.enumeration.model.jmdb.Lga> = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            lgas = repository.getLgas().toMutableList()


            val lgaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                R.layout.spinner_item,
                mutableListOf("Select Lga") + lgas.map { it.lga }
            )
            lgaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ward.adapter = lgaAdapter
        }

        var areas: MutableList<com.smartapps.jmdb.enumeration.model.jmdb.Area> = mutableListOf()







        binding.streetCover.isVisible = true
        binding.areaCover.isVisible = true




        binding.streetCover.setOnClickListener {

            if (binding.area.selectedItem.toString() == "Select Area" || binding.area.selectedItem.toString() == "No area found*") {
                Toast.makeText(context, "Select area first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }



        binding.areaCover.setOnClickListener {

            if (binding.ward.selectedItem.toString() == "Select Lga" || binding.area.selectedItem.toString() == "No lga found*") {
                Toast.makeText(context, "Select lga first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }


        binding.closeStreets.setOnClickListener {
            binding.streetsCard.isVisible = false
        }

        var streets: MutableList<com.smartapps.jmdb.enumeration.model.jmdb.Street> = mutableListOf()

        val streetAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item, mutableListOf("Select Street")
        )
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.street.adapter = streetAdapter


        val areaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item, mutableListOf("Select Area")
        )
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.area.adapter = areaAdapter



        binding.ward.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                //when selected item is ...reset adapters/

                //if location is clicked before adapters are say.. say. select zone first


                binding.streetCover.isVisible = true
                binding.areaCover.isVisible = true

                if(binding.ward.selectedItem == "Select Lga") return


                CoroutineScope(Dispatchers.Main).launch {

                    areas = repository.getAreas(lgas.filter { it.lga.toString() == binding.ward.selectedItem.toString() }[0].lga_id.toString()).toMutableList()


                    binding.areaCover.isVisible = false

                    if(!areas.isEmpty()){

                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        context,
                        R.layout.spinner_item,
                        mutableListOf("Select Area") + areas.map { it.area }
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.area.adapter = adapter

                    }else{
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Area") + mutableListOf("No area found*")
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.area.adapter = adapter

                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })



        binding.area.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                //when selected item is ...reset adapters/

                //if location is clicked before adapters are say.. say. select zone first


                binding.streetCover.isVisible = true

                if(binding.area.selectedItem == "Select Area" || binding.area.selectedItem == "No area found*") return





                CoroutineScope(Dispatchers.Main).launch {
                    streets = repository.getStreets(areas.filter { it.area.toString() ==  binding.area.selectedItem.toString()}[0].id.toString()).toMutableList()

                    binding.streetCover.isVisible = false

                    if(streets.isEmpty()){
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Street") + listOf("No street found*")
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.street.adapter = adapter
                    }else {


                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Street") + streets.map { it.street }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.street.adapter = adapter

                    }

                }




            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })



        //it should be auto loading if error wait 1 second


/*
        val businesses = listOf(Business(business_name = "Add\nBusiness", photo_url = "[0]"))

        var businessesAdapter = BusinessesAdapter {
            if (buildingId != null) {
                startActivity(
                    Intent(context, NewBusinessActivity::class.java).putExtra(
                        "id",
                        buildingId
                    )
                )
            } else {
                Toast.makeText(context, "Save Building First", Toast.LENGTH_SHORT).show()
            }
        }

        binding.businesses.apply {
            adapter = businessesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        businessesAdapter.submitList(businesses)
*/


        val uuid = UUID.randomUUID().toString()
        binding.buildingNumber.setText(uuid)


        binding.done.setOnClickListener {


            if (binding.buildingNumber.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Building Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.buildingName.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Building Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.ownerName.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Owner Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.ownerEmail.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Owner Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.ownerMobileNumber.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Owner Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//


            if (binding.apartmentCategory.selectedItem.toString() == "Select Building Category") {
                Toast.makeText(context, "Select Building Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.apartmentType.selectedItem.toString() == "Select Building Type") {
                Toast.makeText(context, "Select Building Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//
//
            if (binding.ward.selectedItem.toString() == "Select Lga") {
                Toast.makeText(context, "Select Lga", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.area.selectedItem.toString() == "Select Area" || binding.area.selectedItem.toString() == "No area found*"){
                Toast.makeText(context,"Select Area", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(binding.street.selectedItem.toString() == "Select Street" || binding.street.selectedItem.toString() == "No street found*"){
                Toast.makeText(context,"Select Street", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


//
//
//            if (binding.street.selectedItem.toString() == "Loading Streets") {
//                Toast.makeText(context, "Waiting for streets to load", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//
//
//
//
//
//
//
//
            if (binding.noOfApartments.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Number of Apartment", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//
//
//
            if (binding.latitude.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Latitude", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.longitude.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Longitude", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//
            if (binding.revenueItems.text.toString().isEmpty()) {
                Toast.makeText(context, "Add Revenue Items", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (mImageUri1 == null) {
                Toast.makeText(context, "Capture an Image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }




            AlertDialog.Builder(context).setMessage("Are you sure you want to save this building")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        binding.loadingLayout.isVisible = true

                        val data = Data(
                        approval_code = binding.approvalCode.text.toString(),
                        apartment_type = apartmentTypes.filter { it.building_type == binding.apartmentType.selectedItem.toString() }[0].idbuilding_types, //...
                        building_category_id = apartmentCategories.filter { it.building_category == binding.apartmentCategory.selectedItem.toString() }[0].idbuilding_category, //...x
                        building_id =  uuid,
                        building_image = mImageUri1!!,
                        building_name = binding.buildingName.text.toString(),
                        building_number = System.currentTimeMillis().toString(),
                        id = 7,
                        latitude = binding.latitude.text.toString(),
                        lga = lgas.filter { it.lga.toString() == binding.ward.selectedItem.toString() }[0].lga_id,
                        longitude = binding.longitude.text.toString(),
                        no_of_apartments = binding.noOfApartments.text.toString(),
                        owner_email = binding.ownerEmail.text.toString(),
                        owner_mobile_no = binding.ownerMobileNumber.text.toString(),
                        owner_name = binding.ownerName.text.toString(),
                        state_id = 32,
                        status = 1,
                        street_id = streets.filter { it.street == binding.street.selectedItem.toString() }[0].idstreet.toInt(),
                        taxitem = displayable.map { it.assessment_item_id }, //tax items
                        tin = TIN,
                        ward = areas.filter { it.area == binding.area.selectedItem.toString() }[0].id
                        )
//
//                        val building = Data(
//                            apartment_type = apartmentTypes.filter { it.building_type == binding.apartmentType.selectedItem.toString() }[0].idbuilding_types,
//                            building_category_id = apartmentCategories.filter { it.building_category == binding.apartmentCategory.selectedItem.toString() }[0].idbuilding_category,
//                            building_id = uuid,
//                            building_image = mImageUri1!!,
//                            building_name = binding.buildingName.text.toString(),
//                            building_number = System.currentTimeMillis().toString(),
//                            id = 7,
//                            latitude = binding.latitude.text.toString(),
//                            lga = wards.filter { it.city == binding.ward.selectedItem.toString() }[0].city_id,
//                            longitude = binding.longitude.text.toString(),
//                            no_of_apartments = binding.noOfApartments.text.toString(),
//                            owner_email = binding.ownerEmail.text.toString(),
//                            owner_mobile_no = binding.ownerMobileNumber.text.toString(),
//                            owner_name = binding.ownerName.text.toString(),
//                            state_id = wards.filter { it.city == binding.ward.selectedItem.toString() }[0].city_id,
//                            status = 1,
//                            street_id = streets.filter { it.street == binding.street.selectedItem.toString() }[0].street_id.toInt(),///
//                            taxitem = displayable.map { it.item_ref.toInt() },
//                            ward = wards.filter { it.city == binding.ward.selectedItem.toString() }[0].city_id,
//                        )



                        repository.createBuildingLocally(data, {

                            buildingId = uuid

                            binding.buildingNumber.isFocusable = false
                            binding.buildingNumber.setOnClickListener {
                                Toast.makeText(
                                    context,
                                    "Cannot Edit: Building Saved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                            binding.loadingLayout.isVisible = false
//                            Toast.makeText(
//                                context,
//                                "Success",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            binding.completeCard.isVisible = true


                        }) {
                            binding.loadingLayout.isVisible = false
                            Toast.makeText(
                                context,
                                "Error $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        p0!!.dismiss()


                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }
                }).show()



        }

    }


    override fun onBackPressed() {

        if (forceClose) {
            super.onBackPressed()
            return
        }

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            binding.addImage.isVisible = false
            binding.progress.isVisible = true

            CoroutineScope(Dispatchers.IO).launch {

                try {
                val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            context.contentResolver,
                            mImageUri2!!
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(contentResolver, mImageUri2)
                }


                //do in background thread
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos)

                val b = baos.toByteArray()


                //     val byteString = ByteString.of(*baos.toByteArray())


                mImageUri1 = prefix + Base64.encodeToString(b, Base64.NO_WRAP)


                baos.close()
//NO_WRAP


                Log.d("ACTIVITYX", "ENCODED A " + mImageUri1 + "ENCODED B ")


                // BitMap is data structure of image file which store the image in memory
                // Set the image in imageview for display
                //   Log.d("ACTIVITYX", "LAUNCH CAM CALLED $mImageUri1 ACTIVITY RESULT")


                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(context).load(mImageUri2)
                        .placeholder(R.color.black).centerCrop().fitCenter()
                        .into(binding.photo)

                    binding.progress.isVisible = false
                    binding.addImage.isVisible = true
                }
            }catch(e: Exception){

               }
            }

        }
    }


    lateinit var currentPhotoPath: String


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)!! //TODO CHECK PERMISSION AND DEVICE TYPES

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(context, "Error Taking Picture", Toast.LENGTH_SHORT).show()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "com.smartapps.jmdb.fileprovider",
                        it
                    )
                    mImageUri2 = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()



       /* if (buildingId != null) {


            repository.getBuildingBusinesses(buildingId!!) { biz ->
                CoroutineScope(Dispatchers.Main).launch {
                val businesses =
                    listOf(Business(business_name = "Add\nBusiness", photo_url = "[0]")) + biz

                var businessesAdapter = BusinessesAdapter {

                    if (it == 0) {


                        startActivity(
                            Intent(context, NewBusinessActivity::class.java).putExtra(
                                "id",
                                buildingId
                            )
                        )


                    } else {
                        startActivity(
                            Intent(context, BusinessInfoActivity::class.java).putExtra(
                                "id",
                                businesses[it].business_id
                            ).putExtra("shop_id", buildingId)
                        )
                    }


                }

                binding.businesses.apply {
                    adapter = businessesAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                }

                businessesAdapter.submitList(businesses)

            }
            }


        }*/


    }


    private fun fetchLocation(callback: (Double, Double) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {


//                if(call){
//                    call = false
                    callback(p0.locations[0].latitude, p0.locations[0].longitude)
                    //  }
                }
            },
            Looper.getMainLooper()
        )


    }



    fun x() {


        lateinit var selectedLanguage: BooleanArray
        var langList = ArrayList<Int>()


        var selectedRevenueItem = listOf<Int>()

        var revenueItem: List<com.smartapps.jmdb.data.model.Result> = listOf()
        var langArray: Array<String> = arrayOf()

        CoroutineScope(Dispatchers.Main).launch {
            revenueItem = repository.getRevenueItems()
            langArray = revenueItem.map { it.assessment_item_name }.toTypedArray()
        }


        binding.add.setOnClickListener {


            binding.items.setText("")

            selectedLanguage = BooleanArray(langArray.size)


            val builder = AlertDialog.Builder(context)

            // set title
            builder.setTitle("Select Revenue Items")

            langList = ArrayList<Int>()


            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(langArray, selectedLanguage,
                DialogInterface.OnMultiChoiceClickListener { dialogInterface, i, b ->
                    // check condition
                    if (b) {
                        // when checkbox selected
                        // Add position  in lang list
                        langList.add(i)
                        // Sort array list
                        Collections.sort(langList)
                    } else {
                        // when checkbox unselected
                        // Remove position from langList
                        langList.remove(Integer.valueOf(i))
                    }
                })
            builder.setPositiveButton(
                "OK"
            ) { dialogInterface, i -> // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop

                for (j in 0 until langList.size) {
                    // concat array value
                    stringBuilder.append(langArray.get(langList.get(j)))
                    // check condition

                    if (j != langList.size - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(",")


                    }

                    //       selectedRevenueItem += j
                }
                // set text on textView
                //        Log.d("REVENUEITEM", selectedRevenueItem.toString())
                binding.revenueItems.setText(stringBuilder.toString())


                //submit to adapter here


                val indexes1 = binding.revenueItems.text.toString().split(",")
                var indexes2: MutableList<Int> = mutableListOf()
                indexes1.forEach {
                    indexes2.add(langArray.indexOf(it))
                }

                displayable = mutableListOf()



                indexes2.forEach {
                    displayable.add(
                        revenueItem[it]
                    )

                    //   binding.items.setText(binding.items.text.toString() + "\n\n${revenueItem[it].revenue_name}, ${revenueItem[it].businessSizes}")

                    binding.items.setText(binding.items.text.toString() + "${revenueItem[it].assessment_item_name}\n")

                }


                Log.d("REVENUE", "1. $indexes1")
                Log.d("REVENUE", "2. $indexes2")
                Log.d("REVENUE", "3. $displayable")

                //     binding.revenueItems.setText("")

                //      taxItemAdapter.submitList(displayable)


            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()
            }
            builder.setNeutralButton(
                "Clear All"
            ) { dialogInterface, i ->
                // use for loop
                for (j in 0 until selectedLanguage.size) {
                    // remove all selection
                    selectedLanguage[j] = false
                    // clear language list
                    langList.clear()
                    // clear text view value
                    binding.revenueItems.setText("")
                }
            }
            // show dialog
            builder.show()


        }


    }


}