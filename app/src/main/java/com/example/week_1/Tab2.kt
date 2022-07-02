package com.example.week_1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var lstFood = listOf<Food>()

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
        val root: View = inflater.inflate(R.layout.fragment_tab2, container, false)

        //cardview code

        lstFood = lstFood.plus(Food("one", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("two", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("three", "cat", R.mipmap.beach3))
        lstFood = lstFood.plus(Food("four", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("five", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("one", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("two", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("three", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("four", "cat", R.mipmap.beach3))
        lstFood = lstFood.plus(Food("five", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("one", "cat", R.mipmap.beach3))
        lstFood = lstFood.plus(Food("two", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("three", "cat", R.mipmap.beach3))
        lstFood = lstFood.plus(Food("four", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("five", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("one", "cat", R.mipmap.beach2))
        lstFood = lstFood.plus(Food("two", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("three", "cat", R.mipmap.beach4))
        lstFood = lstFood.plus(Food("four", "cat", R.mipmap.beach3))
        lstFood = lstFood.plus(Food("five", "cat", R.mipmap.beach4))

        val myrv: RecyclerView = root.findViewById(R.id.recyclerview_id)
        val myAdapter = RecyclerViewAdapter(context,lstFood)
        myrv.layoutManager = GridLayoutManager(context, 3)
        myrv.adapter = myAdapter

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}