package com.example.week_1

import android.telecom.VideoProfile.isVideo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.week_1.databinding.ActivityMainBinding


//class MyPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
//    private val NUM_PAGES = 3
//
//    override fun getItemCount(): Int = NUM_PAGES
//
//    override fun createFragment(position: Int): Fragment {
//
//        return when (position) {
//            0 -> {
//                Tab1.newInstance("Contacts","")
//            }
//            1 -> {
//                Tab2.newInstance("Gallery","")
//            }
//            else -> {
//                Tab3.newInstance("NEW","")
//            }
//        }
//    }
//
//}
