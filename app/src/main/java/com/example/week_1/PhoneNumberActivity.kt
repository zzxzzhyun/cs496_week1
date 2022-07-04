package com.example.week_1

import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.week_1.databinding.ActivityPhoneNumberBinding
import com.example.week_1.databinding.Tab1ListItemBinding
import org.json.JSONArray


class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var homeViewModel: Tab1ViewModel

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

        val value = intent.getIntExtra("id",0)
        val contacts = homeViewModel.getPhoneNumbers("asc")
        val person = contacts[value]

        name.text = person.name
        nickname.text = person.nickname
        phone.text = person.phone
        email.text = person.email
        favorite.text = person.favorite

    }





}