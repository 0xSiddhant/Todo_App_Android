package com.example.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {

    // holds value of date, time and month
    lateinit var myCalender: Calendar

    // Interface invoked when you show the dialog and when you click on any value of dialog
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    var finalDate = 0L
    var finalTime = 0L

    private val categoryTypes = arrayListOf<String>("Personal",
            "Business",
            "Insurance",
            "Shopping",
            "Banking")

    val db by lazy {
       AppDatabase.getDatabase(this)
    }

    lateinit var dateEdt: TextInputEditText
    lateinit var timeInputLay: TextInputLayout
    lateinit var timeEdt: TextInputEditText
    lateinit var spinnerCategory: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        connectToLayout()

        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
        saveBtn.setOnClickListener(this)

        setUpSpinner()
    }

    private fun connectToLayout() {
        dateEdt = findViewById(R.id.dateEdt)
        timeInputLay = findViewById(R.id.timeInputLay)
        timeEdt = findViewById(R.id.timeEdt)
        spinnerCategory = findViewById(R.id.spinnerCategory)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.dateEdt -> {
                setListener()
            }
            R.id.timeEdt -> {
                setTimeListener()
            }
            R.id.saveBtn -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        val category = spinnerCategory.selectedItem.toString()
        val title = titleInpLay.editText?.text.toString()
        val description = taskInpLay.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTask(
                    TodoModel(
                        title,
                        description,
                        category,
                        finalDate,
                        finalTime
                    )
                )
            }
            finish()
        }

    }


    private fun setUpSpinner() {
        val adaptor = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryTypes)
        categoryTypes.sort()
        spinnerCategory.adapter = adaptor
    }

    private fun setTimeListener() {
        myCalender = Calendar.getInstance()

        timeSetListener = TimePickerDialog.OnTimeSetListener { _ : TimePicker, hourOfDay: Int, minute: Int ->
            myCalender.set(Calendar.HOUR, hourOfDay)
            myCalender.set(Calendar.MINUTE, minute)
            updateTime()
        }

        val timePickerDialog = TimePickerDialog(this,
                timeSetListener,
                myCalender.get(Calendar.HOUR_OF_DAY),
                myCalender.get(Calendar.MINUTE),
                false)
        timePickerDialog.show()
    }


    private  fun setListener() {
        myCalender = Calendar.getInstance()

        // Fetching Value Via Callback
        dateSetListener = DatePickerDialog.OnDateSetListener { _ : DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        // Opening DatePicker Dialog
        val datePickerDialog = DatePickerDialog(this,
                dateSetListener,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private  fun updateDate() {

        //m -> min
        //M -> Month
        //Mon, 5,Jun 2021
        val myFormat = "EEE d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        finalDate = myCalender.time.time
        dateEdt.setText(sdf.format(myCalender.time))

        timeInputLay.visibility = View.VISIBLE
    }

    private fun updateTime() {
        //3:32 am
        val myFormat = "h:mm a"
        val sdf = SimpleDateFormat(myFormat)
        finalTime = myCalender.time.time
        timeEdt.setText(sdf.format(myCalender.time))
    }
}