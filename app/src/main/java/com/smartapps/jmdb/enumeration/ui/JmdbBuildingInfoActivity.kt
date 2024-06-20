package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
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
import com.smartapps.jmdb.enumeration.model.jmdb.Data
import com.tombra.ticket.util.myDateFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class JmdbBuildingInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityBuildingInfoBinding

    data class MyStreet(
        val street_id: String,
        val street: String,
    )

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

        repository = JmdbRepository(this)


        id = intent.extras!!.getString("id")!!


        binding.ok.setOnClickListener {
            binding.completeCard.isVisible = false
        }


      //  Log.d(TAG, "Building id $id")

        repository.loadBuildingLocally(id!!, { building ->

            CoroutineScope(Dispatchers.Main).launch {


                binding.sync.setOnClickListener {
                    repository.checkBuildingSyncStatus(building.building_id) {

                        if (it) {

                            CoroutineScope(Dispatchers.Main).launch {
                                AlertDialog.Builder(context)
                                    .setMessage("This building has already been synced")
                                    .setPositiveButton(
                                        "Ok",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                                p0!!.dismiss()
                                            }
                                        }).show()

                            }

                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                AlertDialog.Builder(context)
                                    .setMessage("This building is ready to be synced")
                                    .setPositiveButton(
                                        "Proceed",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                                //show syncing dialog
                                                binding.syncingLayout.isVisible = true
                                                repository.syncSingleBuildings(building,
                                                    {
                                                        CoroutineScope(Dispatchers.Main).launch {
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
                                                        }
                                                    }) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        //add synced success dialog
                                                        binding.completeCard.isVisible = true
                                                        binding.syncingLayout.isVisible = false
                                                        binding.status.text = "Success"
                                                        binding.message.text =
                                                            "Successfully synced this building"
                                                        binding.statusIndicator.setImageResource(R.drawable.round_verified_24)
                                                    }
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
                }


                this@JmdbBuildingInfoActivity.building = building



                binding.loadingLayout.isVisible = false
                //success
                //hide loading layout
           //     Log.d(TAG, "Building fetched")

                binding.print.setOnClickListener {
                    doWebPrint(this@JmdbBuildingInfoActivity)
                }

                buildingId = building.building_id


//            Glide.with(this).load(building.building_image)
//                .placeholder(R.color.black).centerCrop().fitCenter()
//                .into(binding.photo)

                try {
                    val byte = Base64.decode(
                        building.building_image.replaceFirst(prefix, ""),
                        Base64.DEFAULT
                    )
                    val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
                    binding.photo.setImageBitmap(bitmap)
                } catch (e: Exception) {

                }



                with(building) {
                    binding.approvalCode.setText(if(approval_code.trim().replace(" ","").isEmpty()){"Not set"}else{approval_code})


                    binding.buildingNumber.setText(building_number)
                    binding.buildingName.setText(building_name)

                    binding.apartmentType.setText(
                        repository.getBuildingTypes()
                            .filter { it.idbuilding_types == apartment_type }[0].building_type
                    )

                    binding.apartmentCategory.setText(
                        repository.getBuildingCategories()
                            .filter { it.idbuilding_category == building_category_id }[0].building_category
                    )
                    binding.ownerEmail.setText(owner_email)
                    binding.ownerMobileNumber.setText(owner_mobile_no)
                    binding.ownerName.setText(owner_name)


                    binding.state.setText(
                        "Jos"
                    )

//                    val strs = repository._getStreetsWithWard()
//                        .filter { it.wardId == state_id.toString() }[0].streets
//
                    try {
                    binding.street.setText( repository.getStreets2().toMutableList().filter { it.idstreet == street_id }[0].street)
                    }catch (e:Exception){

                    }
                    binding.lga.setText( repository.getLgas().filter { it.lga_id == lga }[0].lga )

//                    strs.split("##").forEach {
//
//                        if (it.isNotEmpty()) {
//
//                            Log.d("TRACER", "STREET: ${it}")
//                            try {
//                                val inner = it.split("@@")
//                                streets.add(
//                                    MyStreet(
//                                        inner[0],
//                                        inner[1],
//                                    )
//                                )
//
//
//                            } catch (e: Exception) {
//                                Log.d("TRACER", "${e.message.toString()}")
//                            }
//                        }
//                    }

//                    binding.street.setText(streets.filter { it.street_id == street_id.toString() }[0].street)


                    //do conversion here
                    // binding.street.setText(streets.filter {  })
                    binding.noOfApartments.setText(no_of_apartments)
                    //  binding.apartmentCount.setText(apartment_count)
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
//                CoroutineScope(Dispatchers.IO).launch {
//
                   val ri = repository.getRevenueItems()

                     Log.d("MYREVITEMS", ri.toString())
                Log.d("MYREVITEMS","Tax items" +items)//duplex, luxury apartment, laundry cars
                    CoroutineScope(Dispatchers.Main).launch {

                        items.forEach { id ->
                            try {
                                binding.items.setText(binding.items.text.toString() + "\n${ri.filter { it.assessment_item_id == id }[0].assessment_item_name}")
                            }catch(e:Exception){
                           //     binding.items.setText(binding.items.text.toString() + "\n${ri.filter { it.assessment_item_id == id }[0].assessment_item_name}")
                            }
                        }
                    }

//                }


            }


        }) {
            CoroutineScope(Dispatchers.Main).launch {
                //error
                //hide loading layout
                binding.loadingLayout.isVisible = false
                Toast.makeText(this@JmdbBuildingInfoActivity, "Error $it", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            //error
        }


    }


    private fun print() {
        Toast.makeText(this, "Printing", Toast.LENGTH_SHORT).show()
    }


    private var mWebView: WebView? = null



    fun doWebPrint(context: Context) {
        val webView = WebView(context)

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ) = false

            override fun onPageFinished(view: WebView?, url: String?) {

                createWebPrintJob(view!!)
                mWebView = null
            }

        }





        val htmlDocument = getHtmlContent()
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)
        mWebView = webView





    }


    fun createWebPrintJob(webView: WebView) {


        (getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
                .also { printJob ->
                    //  printJobs += printJob,,save later for status checking
                }

        }

//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
//            val printManager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
//            val printAdapter: PrintDocumentAdapter = webView.createPrintDocumentAdapter("")
//            printManager.print(" My Print Job ", printAdapter, PrintAttributes.Builder().build())
//        }


    }








    fun getHtmlContent(): String {

        //TODO() Check if COMPANY or INDIVIDUAL

//
//        val html = "<html>" +
//                "<body>" +
//                "" +
//                "<h1>F.C.T.A</h1>" +
//                "'\n" +
//                "<p>Federal Capital Territory Administration Abuja</p>" +
//                "\n" +
//                "<p>Receipt no. 2252/1156/00802 ${date}\n</p>" +
//                "<p>!!!!! COPY !!!!!</p>" +
//                "\n" +
//                "<p><b>Payers Name</b>[R]<b>${binding.payersName.text.toString()}</b></p>" +
//                "\n" +
//                "<p><b>Vehicle Number</b>[R]<b>${binding.vehicleNumber.text.toString()}</b></p>" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "<p><b>Truck Size</b>[R]<b>${binding.price.selectedItem.toString()}</b></p>" +
//                "\n" +
//                "<p>--------------------------------</p>" +
//                "\n" +
//                "<p><b>Total amount :[R]${mainAmount} NGN</b></p>" +
//                "</body>" +
//                "</html>"


        return "<html>" +
                "<body>" +
                "<p style='margin-left:20px; font-size:14px; font-family:monospace'>SmartRev</p>" +
                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='font-size:14px; font-family:monospace'>Receipt no. 2252/1156/00802 ${myDateFormatter(System.currentTimeMillis().toString())}<br>" +
                "!!!!! COPY !!!!!</p>" +


                "<br>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Building Name <span style='float:right'>${binding.buildingName.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Owner Name <span style='float:right'>${binding.ownerName.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Owner Email <span style='float:right'>${binding.ownerEmail.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Owner Mobile Number <span style='float:right'>${binding.ownerMobileNumber.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Apartment Category <span style='float:right'>${binding.apartmentCategory.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Apartment Type<span style='float:right'>${binding.apartmentType.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>State <span style='float:right'>${binding.state.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Number of Apartment <span style='float:right'>${binding.noOfApartments.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Latitude <span style='float:right'>${binding.latitude.text.toString()}</span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Longitude<span style='float:right'>${binding.longitude.text.toString()}</span></b></p>" +

                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px; font-size:14px; font-family:monospace'><b>Revenue Items: <span style='float:right'></span></b></p>" +


                "<p style='font-size:14px; font-family:monospace'></p>" +
                "<p style='text_align:left; margin-left:3px;margin-right:20px; font-size:14px; font-family:monospace'><b>${binding.items.text.toString()}</b></p>" +



                "<br>" +
                "<br>" +
                "<p align='center' style='font-size:14px; font-family:monospace'><b>********************************</b></p>" +
                "<p align='center' style='font-size:15px;'><b>Thank You</b></p>" +
                "<p align='center' style='font-size:14px; font-family:monospace'><b>********************************</b></p>" +
                "<p></p>" +
                "</body>" +
                "</html>"
    }


    override fun onResume() {
        super.onResume()

       // Log.d(TAG, "Building id 2 -- $id")

      /*  repository.getBuildingBusinesses(id!!) { biz ->
            CoroutineScope(Dispatchers.Main).launch {

                binding.linked.setText("Linked Businesses: (${biz.size})")
                val businesses =
                    listOf(Business(business_name = "Add\nBusiness", photo_url = "[0]")) + biz

                var businessesAdapter = BusinessesAdapter {

                    if (it == 0) {


                        if (id != null) {
                            startActivity(
                                Intent(
                                    this@JmdbBuildingInfoActivity,
                                    NewBusinessActivity::class.java
                                ).putExtra(
                                    "id",
                                    buildingId
                                )
                            )
                        } else {
                            Toast.makeText(context, "Save Building First", Toast.LENGTH_SHORT)
                                .show()
                        }


                    } else {
                        startActivity(
                            Intent(
                                this@JmdbBuildingInfoActivity,
                                BusinessInfoActivity::class.java
                            ).putExtra(
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
        }*/


    }


}