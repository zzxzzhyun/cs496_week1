package com.example.week_1

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.week_1.databinding.ActivityMainBinding
import com.example.week_1.databinding.FragmentTab3Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

   val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS)

    var curRes: String = ""
    var curLon : Double = 0.0
    var curLat : Double = 0.0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        checkPermission()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.apply {
            adapter = MyPagerAdapter(context as FragmentActivity)
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0-> {
                    tab.text = "Contact"
                    tab.setIcon(R.drawable.ic_baseline_people)}
                1-> {
                    tab.text = "Gallery"
                    tab.setIcon(R.drawable.ic_baseline_image)}
                2-> {
                    tab.text = "Map"
                    tab.setIcon(R.drawable.ic_baseline_image)
                }
            }
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0-> {
                    binding.textView.text = "Contact"
                    binding.viewPager.isUserInputEnabled = true;
                    }
                    1-> {
                    binding.textView.text = "Gallery"
                    binding.viewPager.isUserInputEnabled = true;
                    }
                    2-> {
                    binding.textView.text = "Map"
                    binding.viewPager.isUserInputEnabled = false;
                    }
                }

            }
        })

        curRes = intent.getStringExtra("name").toString()
        curLon = intent.getDoubleExtra("lon", 0.0)
        curLat = intent.getDoubleExtra("lat", 0.0)
        if (curRes != null) {
            Log.d("mainname", curRes)
        }

        if (curLon > 1) {

//            this?.supportFragmentManager!!.beginTransaction()
//                .replace(R.id.appBarLayout, Tab3())
//                .commit()
            binding.viewPager.setCurrentItem(2, false)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() {
        if (!isPermitted()) {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted() : Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    inner class MyPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        private val NUM_PAGES = 3

        override fun getItemCount(): Int = NUM_PAGES

        @RequiresApi(Build.VERSION_CODES.M)
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val tab1 = Tab1.newInstance("Contacts","")
                    return tab1
                }
                1 -> {
                    val tab2 = Tab2.newInstance("Contacts","")
                    return tab2
                }
                else -> {
                    val tab3 = Tab3.newInstance("Contacts","")
                    var bundle = Bundle()
                    bundle.putDouble("lon", curLon)
                    bundle.putDouble("lat", curLat)
                    bundle.putString("res", curRes)
                    tab3.arguments = bundle
                    curLon = 0.0
                    curLat = 0.0
                    curRes = ""
                    return tab3
                }
            }
        }



    }


}