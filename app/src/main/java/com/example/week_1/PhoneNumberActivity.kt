package com.example.week_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray


class PhoneNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)

        val textView: TextView = findViewById(R.id.numberView)

        val jsonString = this.assets?.open("phoneNumber.json")?.reader()?.readText()
        val jsonarray = JSONArray(jsonString)
        val value = intent.getIntExtra("id",0)
        val person = jsonarray.getJSONObject(value)

        textView.text = person.getString("Mobile Phone")


    }





}