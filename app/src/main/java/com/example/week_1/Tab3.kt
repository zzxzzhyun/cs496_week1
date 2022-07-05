package com.example.week_1

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.week_1.databinding.FragmentTab3Binding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMap.OnMapClickListener
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.PanelSlideListener
import com.sothree.slidinguppanel.PanelState
import com.sothree.slidinguppanel.ScrollableViewHelper
import org.json.JSONArray


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab3 : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var selLon: Double? = null
    var selLat: Double? = null
    var selRes: String? = null
    var curLon: Double? = null
    var curLat: Double? = null
    var tab3Clicked: Boolean = false

    var array = mutableListOf<Restaurant>()
    var images = arrayOf(
        R.mipmap.food1, R.mipmap.food2, R.mipmap.food3, R.mipmap.food4, R.mipmap.food5, R.mipmap.food6,
        R.mipmap.food7, R.mipmap.food8, R.mipmap.food9, R.mipmap.food10, R.mipmap.food11, R.mipmap.food12,
        R.mipmap.food13, R.mipmap.food14, R.mipmap.food15, R.mipmap.food16, R.mipmap.food17, R.mipmap.food18,
        R.mipmap.food19, R.mipmap.food20, R.mipmap.food21, R.mipmap.food22, R.mipmap.food23, R.mipmap.food24,
        R.mipmap.food25, R.mipmap.food26, R.mipmap.food27, R.mipmap.food28, R.mipmap.food29, R.mipmap.food30
    )


    private val marker = Marker()
//    private val marker1 = Marker()
//    private val marker2 = Marker()

    private lateinit var binding: FragmentTab3Binding

    private lateinit var mainActivity: MainActivity
    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        binding = FragmentTab3Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View = inflater.inflate(R.layout.fragment_tab3, container, false)
        val list: View = inflater.inflate(R.layout.tab3_list_item, container, false)
        val jsonString = activity?.assets?.open("storeData.json")?.reader()?.readText()
        val jsonarray = JSONArray(jsonString)
        for (i in 0 until jsonarray.length()){
            val store = jsonarray.getJSONObject(i)
            array.add(Restaurant(store.getString("name"), store.getString("location"), store.getString("category"), store.getDouble("lon"), store.getDouble("lat"),arrayOf(images[2 * i], images[2 * i + 1])))
        }

        val listView : ListView = root.findViewById(R.id.tab3ListView)
        val searchView: SearchView = root.findViewById(R.id.idSV)
        val adapter = Tab3ListViewAdapter(array)
        listView.adapter = adapter

        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    adapter.filter.filter(s)
                    Log.d(TAG, "SearchVies Text is changed : $s")
                    listView.adapter = adapter
//                    binding.phonelistview.layoutManager = LinearLayoutManager(context)
                    return false
                }
            }
        searchView.setOnQueryTextListener(searchViewTextListener)

        val slidePanel = binding.mainFrame                    // SlidingUpPanel
        slidePanel.addPanelSlideListener(PanelEventListener())
        slidePanel.setScrollableViewHelper(NestedScrollableViewHelper())
//        val distance =  calDist(latitude, logitude, curLat!!, curLon!!)
//        val dist: TextView = list.findViewById(R.id.distance)
//        dist.setText("${dist} km")

        listView.setOnItemClickListener { parent, view, position, id ->
            var state = slidePanel.panelState
            var layout: View? = root.findViewById(R.id.slide_layout)
            if (layout != null) {
                layout.performClick()
            }
            val latitude = array[position].lat
            val logitude = array[position].lon
            Log.d("Lat", latitude.toString())
            Log.d("Lon", logitude.toString())
            moveCam(latitude, logitude)
            marker.position = LatLng(latitude, logitude)
            marker.map = naverMap
            marker.captionText = array[position].name
        }
        return root
    }

    inner class PanelEventListener : PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View, slideOffset: Float) {
//            TODO("Not yet implemented")
        }

        override fun onPanelStateChanged(
            panel: View,
            previousState: PanelState,
            newState: PanelState
        ) {
            if (newState == PanelState.COLLAPSED) {
                binding.mainFrame.isEnabled = false
            } else if (newState == PanelState.EXPANDED) {
                binding.mainFrame.isEnabled = true
            }
        }
    }

    inner class NestedScrollableViewHelper : ScrollableViewHelper() {
        override fun getScrollableViewScrollPosition(scrollableView: View?, isSlidingUp: Boolean): Int {
            return if (binding.tab3ListView is NestedScrollView) {
                if (isSlidingUp) {
                    binding.tab3ListView.getScrollY()
                } else {
                    val nsv = binding.tab3ListView as NestedScrollView
                    val child = nsv.getChildAt(0)
                    child.bottom - (nsv.height + nsv.scrollY)
                }
            } else {
                0
            }
        }
    }

    private fun calDist(lat1:Double, lon1:Double, lat2:Double, lon2:Double) : Long{
        val EARTH_R = 6371000.0
        val rad = Math.PI / 180
        val radLat1 = rad * lat1
        val radLat2 = rad * lat2
        val radDist = rad * (lon1 - lon2)

        var distance = Math.sin(radLat1) * Math.sin(radLat2)
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist)
        val ret = EARTH_R * Math.acos(distance)

        return Math.round(ret) // 미터 단위
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        selLon = arguments?.getDouble("lon")
        selLat = arguments?.getDouble("lat")
        selRes = arguments?.getString("res")

        Log.d("lon", selLon.toString())
        Log.d("lat", selLat.toString())
        if(selRes != null) {
            Log.d("res", selRes!!)
        }
        super.onActivityCreated(savedInstanceState)
    }

    fun moveCam(lon : Double, lat : Double){
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(lon, lat)).animate(CameraAnimation.Fly, 1000)
        naverMap.moveCamera(cameraUpdate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        val uiSettings: UiSettings = naverMap.getUiSettings()
        uiSettings.setLocationButtonEnabled(true)

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Face

        naverMap.addOnLocationChangeListener { location ->
            curLat = location.latitude
            curLon = location.longitude
//            Toast.makeText(mainActivity, "${location.latitude}, ${location.longitude}",
//                Toast.LENGTH_SHORT).show()
        }

        naverMap.onMapClickListener =
            OnMapClickListener { pointF, latLng ->
                val latitude = latLng.latitude
                val logitude = latLng.longitude
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, logitude))
                naverMap.moveCamera(cameraUpdate)
                Log.w("tag", "@@@@@@ latitude $latitude")
                Log.w("tag", "@@@@@@ logitude $logitude")
            }

//        val cameraUpdate = CameraUpdate.scrollTo(LatLng(36.2424, 126.9783881))
//        naverMap.moveCamera(cameraUpdate)

        if (selLon != null && selLon!! > 1) {
            moveCam(selLat!!, selLon!!)
            Log.d("move", "good")
            marker.position = LatLng(selLat!!, selLon!!)
            marker.map = naverMap
//            marker.iconTintColor = Color.RED
//            marker.icon = MarkerIcons.BLACK // 마커 검은색으로
            marker.captionText = selRes!!
            marker.width = Marker.SIZE_AUTO
            marker.height = Marker.SIZE_AUTO
            selLat = null
            selLon = null
            selRes = null
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
//        lateinit var arguments: Bundle

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}