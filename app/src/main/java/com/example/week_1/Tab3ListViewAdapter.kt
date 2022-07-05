package com.example.week_1

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class Tab3ListViewAdapter(private val items: MutableList<Restaurant>): BaseAdapter(), Filterable {

    var TAG = "Tab3ListViewAdapter"

    var filteredStores = ArrayList<Restaurant>()
    var itemFilter = ItemFilter()

    init {
        filteredStores.addAll(items)
    }
    override fun getCount(): Int = filteredStores.size

    override fun getItem(position: Int): Restaurant = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.tab3_list_item, parent, false)

        var img : ImageView? = convertView?.findViewById<ImageView>(R.id.imageView)
        var name : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_name)
        var category : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_category)
        var location: TextView? = convertView?.findViewById<TextView>(R.id.location)

        var item: Restaurant = filteredStores[position]

        if(img != null) {
            img.setImageResource(item.pic[0])
        }
        if (name != null) {
            name.setText(item.name)
        }
        if (category != null) {
            category.setText(item.category)
        }
        if (location != null) {
            location.setText(item.location)
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

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<Restaurant> = ArrayList<Restaurant>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = items
                results.count = items.size

                return results
                //공백제외 2글자 이하인 경우 -> 이름으로만 검색
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
}