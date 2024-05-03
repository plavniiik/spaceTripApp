package com.application.noteproject.utils.validation

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.application.tripapp.databinding.FragmentAsteroidsBinding
import java.util.Calendar

class CalendarHelper<T: ViewBinding>(private val context: Context, private val binding: T?) {

    fun getStartDatePicker(inputSearch: EditText): View.OnClickListener {
        return View.OnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)
            val mMonth: Int = c.get(Calendar.MONTH)
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    inputSearch.setText(
                        "${year}-${monthOfYear + 1}-${dayOfMonth}"
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
    }

    fun getEndDatePicker(inputSearchEnd: EditText): View.OnClickListener {
        return View.OnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)
            val mMonth: Int = c.get(Calendar.MONTH)
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    inputSearchEnd.setText(
                        "${year}-${monthOfYear + 1}-${dayOfMonth}"
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
    }
}
