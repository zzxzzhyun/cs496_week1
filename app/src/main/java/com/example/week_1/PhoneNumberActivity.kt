package com.example.week_1

import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.week_1.databinding.ActivityPhoneNumberBinding
import com.example.week_1.databinding.Tab1ListItemBinding
import org.json.JSONArray


class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_phone_number)

        val name: TextView = findViewById(R.id.userNameDetail)
        val nickname: TextView = findViewById(R.id.userNicknameDetail)
        val phone: TextView = findViewById(R.id.userPhoneDetail)
        val email: TextView = findViewById(R.id.userEmailDetail)
        val favorite: TextView = findViewById(R.id.userFavoriteDetail)

        name.text = intent.getStringExtra("Name")
        phone.text = intent.getStringExtra("Phone")
        email.text = intent.getStringExtra("Email")

    }





}