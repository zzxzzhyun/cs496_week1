package com.example.week_1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.week_1.databinding.FragmentTab3Binding
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMap.OnMapClickListener
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.PanelSlideListener
import com.sothree.slidinguppanel.PanelState
import com.sothree.slidinguppanel.ScrollableViewHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
    var keyBoardClosed: Boolean = false

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
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    var mLastLocation: Location? = null// 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var listView: ListView
    lateinit var layout: View
    private lateinit var adapter : Tab3ListViewAdapter

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
        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        startLocationUpdates()
    }

    @SuppressLint("ClickableViewAccessibility")
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

        listView = root.findViewById(R.id.tab3ListView)
        val searchView: SearchView = root.findViewById(R.id.idSV)
        adapter = Tab3ListViewAdapter(array, mLastLocation)
        listView.adapter = adapter

        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
//                    hideKeyboard(view)
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

        searchView.setOnClickListener {
            hideKeyboard(searchView)
            Log.d("clicked", "click")
        }
        searchView.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager =
                    mainActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager!!.hideSoftInputFromWindow(v!!.windowToken, 0)
            } else {
                hideKeyboard(searchView)
                Log.d("clicked", "click")
            }
        })

//        val touchInterceptor = root.findViewById(R.id.touchInterceptor) as FrameLayout
//        touchInterceptor.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                if (searchView.isFocused()) {
//                    val outRect = Rect()
//                    searchView.getGlobalVisibleRect(outRect)
//                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
//                        searchView.clearFocus()
//                        val imm =
//                            v.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                        imm.hideSoftInputFromWindow(v.windowToken, 0)
//                    }
//                }
//            }
//            false
//        }
        searchView.setOnQueryTextListener(searchViewTextListener)
        searchView.setFocusable(true)
        searchView.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {

                } else {
                    if( v != null) {
                        hideKeyboard(v!!)
                    }

                }
            }
        }
        )

        val slidePanel = binding.mainFrame                    // SlidingUpPanel
        slidePanel.addPanelSlideListener(PanelEventListener())
        slidePanel.setScrollableViewHelper(NestedScrollableViewHelper())

        listView.setOnItemClickListener { parent, view, position, id ->
            hideKeyboard(view)
            layout = root.findViewById(R.id.slide_layout)
            val latitude = adapter.getItem(position).lat
            val logitude = adapter.getItem(position).lon
            Log.d("Lat", latitude.toString())
            Log.d("Lon", logitude.toString())
            moveCam(latitude, logitude)
            marker.position = LatLng(latitude, logitude)
            marker.map = naverMap
            marker.captionText = adapter.getItem(position).name

            layout.performClick()
            layout.performClick()
        }
        return root
    }

    private fun hideKeyboard(view: View) {
        if (view != null) {
            val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
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
                Log.d("close", "Closed")
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

    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity)
        if (ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
//        text2.text = "위도 : " + mLastLocation.latitude // 갱신 된 위도
//        text1.text = "경도 : " + mLastLocation.longitude // 갱신 된 경도
        adapter = Tab3ListViewAdapter(array, location)
        listView.adapter = adapter

    }

    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(mainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
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