package com.example.week_1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Tab1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var sortText = "asc"
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS)

    private lateinit var homeViewModel: Tab1ViewModel
    private lateinit var root : View
    lateinit var phones : ListView
    lateinit var initInflater : LayoutInflater
    var initContainer : ViewGroup? = null
    var initSavedInstanceState : Bundle? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkPermission()
        initInflater = inflater
        if (container != null) {
            initContainer = container
        }
        if (savedInstanceState != null) {
            initSavedInstanceState = savedInstanceState
        }

        homeViewModel =
            ViewModelProvider(this).get(Tab1ViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_tab1, container, false)

        //setList()
        phones = root.findViewById(R.id.list_item)
        val allnumbers = homeViewModel.getPhoneNumbers(sortText,"")
        val adapter = PhoneAdapter(allnumbers)

        //setAddButtonListener()
        val addButton : FloatingActionButton = root.findViewById(R.id.button_id)
        addButton.setOnClickListener {
            val addButtonIntent = Intent(context, AddContactsActivity::class.java)
            startActivityForResult(addButtonIntent,10001)
        }

        phones.adapter = adapter

        phones.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, PhoneNumberActivity::class.java).apply{
                Log.d("clickedornot", "clicked")
                val item = allnumbers[id.toInt()]
                putExtra("Name", item.name)
                putExtra("Phone", item.phone)
                putExtra("Email", item.email)
                putExtra("Nickname", item.nickname)
                putExtra("Food",item.favorite)
            }
            startActivity(intent)
        }


        return root

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
            onCreateView(initInflater, initContainer, initSavedInstanceState)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() {
        if (!isPermitted()) {
            ActivityCompat.requestPermissions(context as Activity, permissions, 99)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted() : Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context as Activity,
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}