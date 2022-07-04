package com.example.week_1

import android.location.Location
import com.naver.maps.geometry.LatLng
import org.json.JSONArray
import java.util.*

data class Restaurant(var name: String, var location: String, var category: String, var lat: Double, var lon: Double, var pic: Array<Int> )