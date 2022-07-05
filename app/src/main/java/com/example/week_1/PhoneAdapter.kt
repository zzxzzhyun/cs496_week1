package com.example.week_1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class PhoneAdapter(private var items: MutableList<ListViewItem>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): ListViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.tab1_list_item, parent, false)

        var img : ImageView? = convertView?.findViewById<ImageView>(R.id.list_img)
        var name : TextView? = convertView?.findViewById<TextView>(R.id.list_name)
        var number : TextView? = convertView?.findViewById<TextView>(R.id.list_nickname)


        var item: ListViewItem = items[position]

        if(img != null) {
            img.setImageResource(R.drawable.ic_baseline_person_24)
        }
        if (name != null) {
            if(item.name==""){
                item.name = "이름없음"
            }
            name.setText(item.name)
        }
        if (number != null) {
            number.setText(item.phone)
        }

        return convertView
    }

    var data = ArrayList<ListViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.i("PhoneAdapter", "set")
        }

}