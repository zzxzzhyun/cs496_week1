package com.example.week_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.week_1.databinding.ActivityAddContactsBinding
import com.example.week_1.databinding.ActivityMainBinding
import com.example.week_1.databinding.FragmentMyBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactsBinding
    private lateinit var _binding: FragmentMyBinding
    //private val WRITE_CONTACT_PERMISSION_CODE = 100
    //private val contactPermissions = Array<String>(5){""}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        _binding = FragmentMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: FloatingActionButton = binding.doneButton

        button.setOnClickListener {
            saveContact()
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }


    }

    private fun saveContact() {
        val inputName = binding.addName.text.toString()
        val inputNickname = binding.addNickname.text.toString()
        val inputPhoneNumber = binding.addNumber.text.toString()
        val inputEmail = binding.addEmail.text.toString()
        val inputFood = binding.addFood.text.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Name", inputName)
        intent.putExtra("Nickname", inputNickname)
        intent.putExtra("Phone", inputPhoneNumber)
        intent.putExtra("Email", inputEmail)
        intent.putExtra("Food", inputFood)
        this.startActivity(intent)


        val fragInfo = Tab1()
        fragInfo.arguments=bundle
        supportFragmentManager.beginTransaction().add( , fragInfo).commit()

    }

    private fun isWriteContactPermissionEnabled(): Boolean {
        val result: Boolean = binding.addNumber.text.toString()!= null
        return result
    }


}