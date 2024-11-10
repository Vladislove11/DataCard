package com.example.datacard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

class SecondActivity : AppCompatActivity() {

    private lateinit var firstNameTV : TextView
    private lateinit var secondNameTV : TextView
    private lateinit var dateBornTV : TextView
    private lateinit var imageIV: ImageView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        firstNameTV = findViewById(R.id.firstNameTV)
        secondNameTV = findViewById(R.id.secondNameTV)
        dateBornTV = findViewById(R.id.dateBornTV)
        imageIV = findViewById(R.id.imageIV)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        val firstName = intent.getStringExtra("firstName")
        val secondName = intent.getStringExtra("secondName")
        val dateBorn = intent.getStringExtra("dateBorn")
        val image = intent.getStringExtra("image")

        firstNameTV.text = firstName
        secondNameTV.text = secondName

        val dateBor = LocalDate.parse( dateBorn, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val dateNow = LocalDate.parse( LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        var period = Period.between(dateBor, dateNow)
        val time1 = dateBor.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val time2 = dateNow.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        var  resultPeriod = ""
        if(time1 > time2) {
            resultPeriod = "${wordMonth(period.months)}: ${period.months}\nдней: ${period.days}"
        } else {
            period = Period.between(dateNow, dateBor.plusYears(period.years + 1L))
            resultPeriod = "${wordMonth(period.months)}: ${period.months}\nдней: ${period.days}"
        }

        dateBornTV.text = "$dateBorn\nдо дня рождения осталось:\n$resultPeriod"
        imageIV.setImageURI(Uri.parse(image))
    }

    fun wordMonth(num: Int): String{
        var result = ""
        if(num == 1){
           result = "месяц"
        } else if ( num in 1 .. 5) {
            result = "месяца"
        } else {
            result = "месяцев"
        }
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_context, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMenuMain ->{
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}