package com.example.week_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class Tab3ListViewAdapter(private val items: MutableList<Restaurant>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Restaurant = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.tab3_list_item, parent, false)

        var img : ImageView? = convertView?.findViewById<ImageView>(R.id.imageView)
        var name : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_name)
        var category : TextView? = convertView?.findViewById<TextView>(R.id.tab3_list_category)
        var location: TextView? = convertView?.findViewById<TextView>(R.id.location)

        var item: Restaurant = items[position]

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
}