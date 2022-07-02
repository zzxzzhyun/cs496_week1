package com.example.week_1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.json.JSONArray


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var array = mutableListOf<ListViewItem>()
    private val WRITE_CONTACT_PERMISSION_CODE = 100

    //private var _binding: FragmentTab1Binding? = null
    //private val binding get() = _binding!!
    private var contactPermissions = Array<String>(5){""}
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
        val root: View = inflater.inflate(R.layout.fragment_tab1, container, false)

        val jsonString = activity?.assets?.open("phoneNumber.json")?.reader()?.readText()
        val jsonarray = JSONArray(jsonString)
        for (i in 0 until jsonarray.length()){
            val person = jsonarray.getJSONObject(i)
            array.add(ListViewItem(person.getString("Display Name"), person.getString("Nickname"), person.getJSONArray("Favorite"), person.getString("email"), person.getString("Mobile Phone")))
        }

        val listView: ListView = root.findViewById(R.id.list_item)

        val adapter = ListViewAdapter(array)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, PhoneNumberActivity::class.java).apply{
                putExtra("id",id.toInt())
            }
            startActivity(intent)
        }

        val button: Button = root.findViewById(R.id.button_id)
        button.setOnClickListener {
        val intent = Intent(context, AddContactsActivity::class.java)
        startActivity(intent)

        }
        return root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab1.
         */
        // TODO: Rename and change types and number of parameters
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