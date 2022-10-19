package com.example.projectsalma

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button
    lateinit var etName: EditText
    lateinit var txtName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit = findViewById(R.id.btn_submit)
        etName = findViewById(R.id.et_name)
        txtName = findViewById(R.id.txt_name)

        btnSubmit.setOnClickListener {
            if (etName.text.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Harap isi nama terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var name = etName.text.toString()

            val sharedPreference = getSharedPreferences(
                "app_preference", Context.MODE_PRIVATE
            )

            var editor = sharedPreference.edit()
            editor.putString("name", name)
            editor.commit()

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("result", name)
            startActivity(intent)

        }
    }
    override fun onResume() {
        super.onResume()
        etName.text = null
    }
}


