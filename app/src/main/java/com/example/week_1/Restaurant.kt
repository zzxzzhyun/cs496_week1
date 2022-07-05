package com.example.week_1

import android.location.Location
import com.naver.maps.geometry.LatLng
import org.json.JSONArray
import java.util.*

data class Restaurant(var name: String, var location: String, var category: String, var lat: Double, var lon: Double, var pic: Array<Int> )

//"name": "베리신주쿠",
//"location": "대전광역시 유성구 어은동",
//"category": "일본식 돈까스/카레",
//"lon": 36.3630188,
//"lat": 127.3577897