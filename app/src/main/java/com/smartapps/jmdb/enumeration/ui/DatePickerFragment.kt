package com.smartapps.jmdb.enumeration.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(var fullDate: String = "", var callback: (String)-> Unit = {}): DialogFragment(), DatePickerDialog.OnDateSetListener {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(),this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        fullDate = "$year,$month,$day"
        callback(fullDate)
    }





}