package com.example.datacard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val GALLARY_REQUEST = 302
    private var photoUri: Uri? = null

    private lateinit var firstNameET : EditText
    private lateinit var secondNameET : EditText
    private lateinit var dateBornET : EditText
    private lateinit var imageIV: ImageView
    private lateinit var saveBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firstNameET = findViewById(R.id.firstNameET)
        secondNameET = findViewById(R.id.secondNameET)
        dateBornET = findViewById(R.id.dateBornET)
        imageIV = findViewById(R.id.imageIV)
        saveBTN = findViewById(R.id.saveBTN)

        imageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }

        saveBTN.setOnClickListener{
            if (validDate(dateBornET.text.toString().trim())) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("firstName", firstNameET.text.toString())
                intent.putExtra("secondName", secondNameET.text.toString())
                intent.putExtra("dateBorn", dateBornET.text.toString())
                intent.putExtra("image", photoUri.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Формат даты должен соответствовать следующему формату дд.мм.гггг", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageIV = findViewById(R.id.imageIV)
        when(requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK){
                photoUri = data?.data
                imageIV.setImageURI(photoUri)
            }
        }
    }

    fun validDate(text: String) : Boolean {
        val regex1 = "^(3[01]|[12][0-9]|0[1-9]|[1-9])\\.(1[0-2]|0[1-9]|[1-9])\\.[0-9]{4}$".toRegex()
        val result: Boolean = regex1.matches(text)
        return  result
    }
}