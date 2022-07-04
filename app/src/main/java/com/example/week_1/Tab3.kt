package com.example.week_1

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMap.OnMapClickListener
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons


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

    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View = inflater.inflate(R.layout.fragment_tab3, container, false)
        return root
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

        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK // 마커 검은색으로
        marker.iconTintColor = Color.RED // 현재위치 마커 빨간색으로
        marker1.position = LatLng(37.62444907132257, 127.09321109051345)
        marker1.map = naverMap
        marker1.captionText = "화랑대 철도공원"
        marker2.position = LatLng(37.608990485010956, 127.0703518565252)
        marker2.map = naverMap // 고씨네
        marker2.captionText = "중랑천 산책로"

        naverMap.addOnLocationChangeListener { location ->
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

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
        naverMap.moveCamera(cameraUpdate)
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