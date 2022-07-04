package com.example.week_1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Tab1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var sortText = "asc"

    private lateinit var homeViewModel: Tab1ViewModel
    private lateinit var root : View
    lateinit var phones : ListView
    lateinit var initInflater : LayoutInflater
    var initContainer : ViewGroup? = null
    var initSavedInstanceState : Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val adapter = ListViewAdapter(homeViewModel.getPhoneNumbers(sortText))

        //setAddButtonListener()
        val addButton : FloatingActionButton = root.findViewById(R.id.button_id)
        addButton.setOnClickListener {
            val addButtonIntent = Intent(context, AddContactsActivity::class.java)
            startActivityForResult(addButtonIntent,10001)
        }


       // homeViewModel.allNumbers.observe(viewLifecycleOwner
        //) {adapter.data}
        phones.adapter = adapter

        phones.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, PhoneNumberActivity::class.java).apply{
                putExtra("id",id.toInt())
            }
            startActivity(intent)
        }


        return root

    }

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

}