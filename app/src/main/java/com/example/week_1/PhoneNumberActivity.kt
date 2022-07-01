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

    private lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_phone_number)

//        val textView: TextView = findViewById(R.id.numberView)
        val name: TextView = findViewById(R.id.userNameDetail)
        val nickname: TextView = findViewById(R.id.userNicknameDetail)
        val phone: TextView = findViewById(R.id.userPhoneDetail)
        val email: TextView = findViewById(R.id.userEmailDetail)
        val favorite: TextView = findViewById(R.id.userFavoriteDetail)


        val jsonString = this.assets?.open("phoneNumber.json")?.reader()?.readText()
        val jsonarray = JSONArray(jsonString)
        val value = intent.getIntExtra("id",0)
        val person = jsonarray.getJSONObject(value)

        name.text = person.getString("Display Name")
        nickname.text = person.getString("Nickname")
        phone.text = person.getString("Mobile Phone")
        email.text = person.getString("email")

        var favor: String = ""
        for (i in 0..2){
            favor += person.getJSONArray("Favorite").get(i) as CharSequence?
            if(i < 2){
                favor += ", "
            }
        }

        favorite.text = favor
    }





}