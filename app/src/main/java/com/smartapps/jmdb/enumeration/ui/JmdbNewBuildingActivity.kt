package com.smartapps.jmdb.enumeration.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.databinding.ActivityNewBuilding2Binding
import com.smartapps.jmdb.enumeration.data.model.jmdb.Area
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategory
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingType
import com.smartapps.jmdb.enumeration.data.model.jmdb.Data
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga
import com.smartapps.jmdb.enumeration.data.model.jmdb.Street
import com.smartapps.jmdb.enumeration.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*





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



    var taxPayersName = ""

    var buildingId: String? = null


    lateinit var repository: JmdbRepository


    private val permissionCode = 101


    lateinit var context: Context




    lateinit var displayable: MutableList<com.smartapps.jmdb.data.model.Result>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBuilding2Binding.inflate(layoutInflater)
        setContentView(binding.root)





        val jmdbNumber = intent.extras!!.getString("jmdb_number")!!




        repository = JmdbRepository

        context = this



        binding.back.setOnClickListener {
            onBackPressed()
        }

        var x = 0

//        binding.typeStreetCard.setOnClickListener {
//
//            if (x == 0) {
//                binding.streetSelect.isVisible = false
//                binding.streetType.isVisible = true
//                binding.typeStreet.setText("Select")
//                x = 1
//            } else {
//                binding.streetSelect.isVisible = true
//                binding.streetType.isVisible = false
//                binding.typeStreet.setText("Type")
//                x = 0
//            }
//
//        }
//

        binding.ok.setOnClickListener {
            startActivity(Intent(context, HomePageActivity::class.java))
            /*      .addFlags(
                  Intent.FLAG_ACTIVITY_NEW_TASK
              ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))*/
            finish()
        }





        binding.photo.setOnClickListener {
            binding.progress.isVisible = true
            //    binding.addImage.isVisible = false
            dispatchTakePictureIntent()
        }

        binding.addImage.setOnClickListener {
            binding.progress.isVisible = true
            //    binding.addImage.isVisible = false
            dispatchTakePictureIntent()
        }

        x()




        if(Constants.TIN.isEmpty()){
            binding.ownerTinCard.isVisible = false
        }else{
            binding.ownerTin.setText(Constants.TIN)
        }
        binding.tag.setText(jmdbNumber)

//        binding.ownerName.setText(OWNER_NAME)
//        binding.ownerEmail.setText(OWNER_EMAIL)
//        binding.ownerMobileNumber.setText(OWNER_NUMBER)


        try {




        } catch (e: Exception) {
            Toast.makeText(context, "location 1 ${e.message}", Toast.LENGTH_SHORT).show()
        }

        try {

            fetchLocation { X, Y ->
                binding.latitude.setText(X.toString())
                binding.longitude.setText(Y.toString())
            }

        } catch (e: Exception) {
            Toast.makeText(context, "location 2 ${e.message}", Toast.LENGTH_SHORT).show()
        }


        try {
            object : CountDownTimer(2000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    fetchLocation { X, Y  ->
                        binding.latitude.setText(X.toString())
                        binding.longitude.setText(Y.toString())
                    }
                }
            }.start()

        } catch (e: Exception) {
            Toast.makeText(context, "clock ${e.message}", Toast.LENGTH_SHORT).show()
        }

        binding.capture1.setOnClickListener {


            if (!isLocationEnabled(this)) {
                promptEnabledLocation(this)
                return@setOnClickListener
            }

            call = true
            binding.latitude.setHint("Fetching....")
            binding.longitude.setHint("Fetching....")
            fetchLocation { X, Y ->
                binding.latitude.setText(X.toString())
                binding.longitude.setText(Y.toString())
            }
        }

        binding.capture2.setOnClickListener {

            if (!isLocationEnabled(this)) {
                promptEnabledLocation(this)
                return@setOnClickListener
            }

            call = true
            binding.latitude.setHint("Fetching....")
            binding.longitude.setHint("Fetching....")
            fetchLocation { X, Y ->
                binding.latitude.setText(X.toString())
                binding.longitude.setText(Y.toString())
            }
        }


        var apartmentCategories: MutableList<BuildingCategory> =
            mutableListOf()



        repository.getBuildingCategories {

            Log.d("CATEGORIES","$it")

            apartmentCategories = it.toMutableList()

            val apartmentCategoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                R.layout.spinner_item,
                mutableListOf("Select Building Category") + apartmentCategories.map { it.building_category }
            )
            apartmentCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.apartmentCategory.adapter = apartmentCategoriesAdapter

        }


        var apartmentTypes: MutableList<BuildingType> =
            mutableListOf()




        val apartmentTypeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item,
            mutableListOf("Select Building Type")
        )
        apartmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apartmentType.adapter = apartmentTypeAdapter

        binding.buildingTypeCover.isVisible = true




        binding.apartmentCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                //when selected item is ...reset adapters/

                //if location is clicked before adapters are say.. say. select zone first


                binding.buildingTypeCover.isVisible = true

                if (binding.apartmentCategory.selectedItem == "Select Building Category") return





                val id = apartmentCategories.filter {it.building_category == binding.apartmentCategory.selectedItem.toString()}[0].idbuilding_category


                repository.getBuildingTypes(id) {
                    binding.buildingTypeCover.isVisible = false
                    apartmentTypes = it.toMutableList()

                    val apartmentTypeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        context,
                        R.layout.spinner_item,
                        mutableListOf("Select Building Type") + apartmentTypes.map { it.building_type }
                    )
                    apartmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.apartmentType.adapter = apartmentTypeAdapter
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })






        var lgas: MutableList<Lga> = mutableListOf()


        repository.getLgas {
            Log.d("CRASHER", "2")
            lgas = it.toMutableList()

            val lgaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                R.layout.spinner_item,
                mutableListOf("Select Lga") + lgas.map { it.lga }
            )
            lgaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ward.adapter = lgaAdapter

        }


        var areas: MutableList<Area> = mutableListOf()







        binding.streetCover.isVisible = true
        binding.areaCover.isVisible = true




        binding.streetCover.setOnClickListener {

            if (binding.area.selectedItem.toString() == "Select Area" || binding.area.selectedItem.toString() == "No area found*") {
                Toast.makeText(context, "Select area first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        binding.buildingTypeCover.setOnClickListener {

            if (binding.apartmentCategory.selectedItem.toString() == "Select Building Category") {
                Toast.makeText(context, "Select building category first", Toast.LENGTH_SHORT).show()
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


        var streets: MutableList<Street> = mutableListOf()

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

                if (binding.ward.selectedItem == "Select Lga") return




                repository.getAreas(lgas.filter { it.lga.toString() == binding.ward.selectedItem.toString() }[0].lga_id.toString()) {
                    areas = it.toMutableList()

                    binding.areaCover.isVisible = false

                    if (!areas.isEmpty()) {

                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Area") + areas.map { it.area }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.area.adapter = adapter

                    } else {
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

                if (binding.area.selectedItem == "Select Area" || binding.area.selectedItem == "No area found*") return



                repository.getStreets(areas.filter { it.area.toString() == binding.area.selectedItem.toString() }[0].id.toString()) {
                    streets = it.toMutableList()

                    binding.streetCover.isVisible = false

                    if (streets.isEmpty()) {
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Street") + listOf("No street found*")
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.street.setAdapter(adapter)
                    } else {


                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            R.layout.spinner_item,
                            mutableListOf("Select Street") + streets.map { it.street }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.street.setAdapter(adapter)

                    }


                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })


        binding.quickSync.setOnClickListener{

            if(binding.area.selectedItem == "Select Area" || binding.area.selectedItem == "No area found*"){
                Toast.makeText(context,"Select area first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(context).setMessage("Do you want to sync streets for ${binding.area.selectedItem.toString()}")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()


                        binding.loadingLayout.isVisible = true


                        repository.quickSync(areas.filter { it.area == binding.area.selectedItem.toString() }[0].id.toString(),{


                            repository.getStreets(areas.filter { it.area == binding.area.selectedItem.toString() }[0].id.toString()) { strs ->
                                streets = strs.toMutableList()

                                if (strs.isEmpty()) {
                                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                        context,
                                        R.layout.spinner_item,
                                        mutableListOf("Select Street") + listOf("No street found*")
                                    )
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    binding.street.setAdapter(adapter)
                                } else {
                                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                        context,
                                        R.layout.spinner_item,
                                        mutableListOf("Select Street") + strs.map { it.street }
                                    )
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    binding.street.setAdapter(adapter)
                                }

                            }


                            Toast.makeText(context,"Sync successful!", Toast.LENGTH_SHORT).show()


                            binding.loadingLayout.isVisible = false

                        }){
                            binding.loadingLayout.isVisible = false
                            Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
                        }

                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }
                }).show()


        }

        val uuid = UUID.randomUUID().toString()
        binding.buildingNumber.setText(uuid)


        binding.done.setOnClickListener {


//            if (binding.buildingNumber.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Building Number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


            if (binding.buildingName.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Building Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


//            if (binding.ownerName.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Owner Name", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


//            if (binding.ownerEmail.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Owner Email", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


//            if (binding.ownerMobileNumber.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Owner Mobile Number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

//



            if (binding.apartmentCategory.selectedItem.toString() == "Select Building Category") {
                Toast.makeText(context, "Select Building Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.apartmentType.selectedItem.toString() == "Select Building Type") {
                Toast.makeText(context, "Select Building Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (binding.ward.selectedItem.toString() == "Select Lga") {
                Toast.makeText(context, "Select Lga", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.area.selectedItem.toString() == "Select Area" || binding.area.selectedItem.toString() == "No area found*") {
                Toast.makeText(context, "Select Area", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            if (x == 0) {
                if (binding.street.selectedItem.toString() == "Select Street" || binding.street.selectedItem.toString() == "No street found*") {
                    Toast.makeText(context, "Select Street", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                if (binding.streetTyped.text.toString().isEmpty()) {
                    Toast.makeText(context, "Enter Street", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
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



//            if (binding.lineNo.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Line Number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }



//
//
//
//            if (binding.noOfApartments.text.toString().isEmpty()) {
//                Toast.makeText(context, "Enter Number of Apartment", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
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


//            if (binding.items.text.toString().isEmpty()) {
//                Toast.makeText(context, "Add Revenue Items", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }


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
                            apartment_type = if(binding.apartmentType.selectedItem.toString() == "Select Building Type"){0}else{apartmentTypes.filter { it.building_type == binding.apartmentType.selectedItem.toString() }[0].idbuilding_types}, //...
                            building_category_id = if(binding.apartmentCategory.selectedItem.toString() == "Select Building Category"){0}else{apartmentCategories.filter { it.building_category == binding.apartmentCategory.selectedItem.toString() }[0].idbuilding_category}, //...x
                            building_id = uuid,
                            building_image = mImageUri2!!.toString(),
                            building_name = binding.buildingName.text.toString(),
                            building_number = uuid,
                            id = 7,
                            latitude = binding.latitude.text.toString(),
                            lga = lgas.filter { it.lga.toString() == binding.ward.selectedItem.toString() }[0].lga_id,
                            longitude = binding.longitude.text.toString(),
                            no_of_apartments = binding.noOfApartments.text.toString(),
                            owner_email = jmdbNumber, /*binding.ownerEmail.text.toString() + ",,," + if (x == 1) {
                                binding.streetTyped.text.toString()
                            } else {
                                "none"
                            }*/
                            owner_mobile_no = binding.ownerMobileNumber.text.toString(),
                            owner_name = mImageUri1!!,//binding.ownerName.text.toString(),
                            state_id = 32,
                            status = 1,
                            street_id = if (x == 1) {
                                0
                            } else {
                                streets.filter { it.street == binding.street.selectedItem.toString() }[0].idstreet.toInt()
                            },
                            taxitem = if (displayable.isEmpty()) {
                                listOf(0)
                            } else {
                                displayable.map { it.assessment_item_id }
                            }, //tax items
                            tin = Constants.TIN,
                            ward = areas.filter { it.area == binding.area.selectedItem.toString() }[0].id,
                            line = "Area line " + binding.lineNo.text.toString()
                        )


                        repository.createBuildingLocally(data, {

                            buildingId = uuid

                            binding.buildingNumber.isFocusable = false



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



        fun bp() {
            startActivity(Intent(context, HomePageActivity::class.java))
            finish()
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
            //   binding.addImage.isVisible = false
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


                    mImageUri1 = Constants.prefix + Base64.encodeToString(b, Base64.NO_WRAP)


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
                } catch (e: Exception) {

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
                        this, "com.smartapps.jmdbb.fileprovider",
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
        binding.progress.isVisible = false
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

        val task = repository.fusedLocationProviderClient.requestLocationUpdates(
            repository.locationRequest,
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

    lateinit var resultLauncher: ActivityResultLauncher<Intent>


    fun x() {



        lateinit var selectedLanguage: BooleanArray
        var langList = ArrayList<Int>()


        var selectedRevenueItem = listOf<Int>()

        var revenueItem: List<com.smartapps.jmdb.data.model.Result> = listOf()
        var langArray: Array<String> = arrayOf()

        displayable = mutableListOf()


        repository.getRevenueItems {
            revenueItem = it
        }


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                Log.d("RESULSTS", "CALLED")
                if (result.resultCode == RESULT_OK) {
                    Log.d("RESULSTS", "SUCCESS")
                    val data = result.data
                    Log.d("RESULSTS", "DATA: $data")
                    if (data != null) {
                        Log.d("RESULSTS", "NOT NULL ${Constants.SELECTED_ITEMS}")
                        val returnedResult = data.getStringExtra("")
                        //  displayable = SELECTED_ITEMS.toList()
                        displayable = mutableListOf()
                        binding.items.setText("")

                        Constants.SELECTED_ITEMS.forEach { id ->
                            binding.items.setText(binding.items.text.toString() + revenueItem.filter { it.assessment_item_id == id }[0].assessment_item_name + "\n")
                            displayable.add(revenueItem.filter { it.assessment_item_id == id }[0])
                            Log.d(
                                "RESULSTS",
                                "${revenueItem.filter { it.assessment_item_id == id }[0]}"
                            )
                        }

                        //display rev item names here

                    } else {

                    }

                }

            }








        binding.all.setOnClickListener {


            if (true) {
                val intent = Intent(context, RevenueItemsActivity::class.java)
                resultLauncher.launch(intent)
                return@setOnClickListener
            }

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


fun isLocationEnabled(context: Context): Boolean {

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var gpsEnabled: Boolean
    var networkEnabled: Boolean

    try {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: Exception) {
        e.printStackTrace()
        gpsEnabled = false
    }


    try {
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    } catch (e: Exception) {
        e.printStackTrace()
        networkEnabled = false
    }

    return gpsEnabled || networkEnabled


}


fun promptEnabledLocation(context: Context) {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    context.startActivity(intent)
}



fun localLocation(context: Context, callback: (Double, Double) -> Unit){
    Log.d("MYLOCATION", "CLICKED")
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("MYLOCATION", "RETURNED")
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }

    Log.d("MYLOCATION", "IN")
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object : LocationListener{
        override fun onLocationChanged(location: Location) {
            Log.d("MYLOCATION", "1. $location")
            location?.let {
                Log.d("MYLOCATION", "2. $location")
                callback(it.latitude, it.longitude)
            }
        }
    })

}
