package com.example.week_1

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import kotlin.math.log
import kotlin.properties.Delegates

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

    val LAT: String? = null
    val LON: String? = null
    val RESTAURANT: String? = null

    private var param1: String? = null
    private var param2: String? = null

    var getResultRestaurant: ActivityResultLauncher<Intent> ?= null

    var lon: Long = 0
    var lat: Long = 0
    var res: String = ""
    var lstFood = listOf<Food>()
    var storeArr = mutableListOf<Restaurant>()
    var images = arrayOf(
        R.mipmap.food1, R.mipmap.food2, R.mipmap.food3, R.mipmap.food4, R.mipmap.food5, R.mipmap.food6,
        R.mipmap.food7, R.mipmap.food8, R.mipmap.food9, R.mipmap.food10, R.mipmap.food11, R.mipmap.food12,
        R.mipmap.food13, R.mipmap.food14, R.mipmap.food15, R.mipmap.food16, R.mipmap.food17, R.mipmap.food18,
        R.mipmap.food19, R.mipmap.food20, R.mipmap.food21, R.mipmap.food22, R.mipmap.food23, R.mipmap.food24,
        R.mipmap.food25, R.mipmap.food26, R.mipmap.food27, R.mipmap.food28, R.mipmap.food29, R.mipmap.food30
    )

    public fun getRestaurant(intent: Intent){
        getResultRestaurant?.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        res = arguments?.getString("restaurant").toString()
//        lon = arguments?.getLong("lon")!!
//        lat = arguments?.getLong("lat")!!
//        Log.d("lon", lon.toString())
//        Log.d("lat", lat.toString())
//        Log.d("restaurant", res)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_tab2, container, false)

        val jsonString = activity?.assets?.open("storeData.json")?.reader()?.readText()
        val jsonarray = JSONArray(jsonString)
        for (i in 0 until jsonarray.length() * 2){
            val store = jsonarray.getJSONObject(i/2)
            val pic : Array<String> = arrayOf()
            storeArr.add(Restaurant(
                store.getString("name"),
                store.getString("location"),
                store.getString("category"),
                store.getDouble("lon"),
                store.getDouble("lat"),
                arrayOf(images[i])
            ))
        }
        storeArr.shuffle()
        val myrv: RecyclerView = root.findViewById(R.id.recyclerview_id)
        val myAdapter = RecyclerViewAdapter(context, storeArr)
        myrv.layoutManager = GridLayoutManager(context, 3)
        myrv.adapter = myAdapter

        return root
    }

    override fun onResume() {
        super.onResume()
//        Log.d("lon", lon.toString())
//        Log.d("lat", lat.toString())

//        val mIntent = Intent(context, FoodActivity::class.java)
//        getResultRestaurant!!.launch(mIntent)
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