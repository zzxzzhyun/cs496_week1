package com.example.week_1

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.xml.sax.helpers.LocatorImpl
import kotlin.math.round

class Tab3ListViewAdapter(private val items: MutableList<Restaurant>, private val loc: Location?): BaseAdapter(), Filterable {

    var TAG = "Tab3ListViewAdapter"

    var filteredStores = ArrayList<Restaurant>()
    var itemFilter = ItemFilter()

    init {
        filteredStores.addAll(items)
    }
    override fun getCount(): Int = filteredStores.size

    override fun getItem(position: Int): Restaurant = filteredStores[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.tab3_list_item, parent, false)

        var name : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_name)
        var category : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_category)
        var location: TextView? = convertView?.findViewById<TextView>(R.id.location)
        var distance:TextView? = convertView?.findViewById(R.id.distance)

        var item: Restaurant = filteredStores[position]

        if (name != null) {
            name.setText(item.name)
        }
        if (category != null) {
            category.setText(item.category)
        }
        if (location != null) {

            location.setText(item.location)
        }
        if(distance != null && loc != null){
            val dist = calDist(item.lat, item.lon, loc.latitude, loc.longitude)
            Log.d("dist", dist.toString())
            distance.setText("${round(dist / 100.0) / 10.0} km")
        }
        return convertView

    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //????????? ???????????? ????????? ?????? ?????? ????????? ??????
            val filteredList: ArrayList<Restaurant> = ArrayList<Restaurant>()
            //???????????? ????????? ?????? ?????? ?????? -> ?????? ??????
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = items
                results.count = items.size

                return results
                //???????????? 2?????? ????????? ?????? -> ??????????????? ??????
            } else {
                for (item in items) {
                    if (item.name.contains(filterString)) {
                        filteredList.add(item)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            filteredStores.clear()
            filteredStores.addAll(filterResults.values as ArrayList<Restaurant>)
            println(filterResults.values)
            notifyDataSetChanged()
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

        return Math.round(ret) // ?????? ??????
    }
}