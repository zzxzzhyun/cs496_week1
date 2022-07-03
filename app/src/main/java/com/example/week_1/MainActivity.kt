package com.example.week_1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.week_1.databinding.ActivityMainBinding
import com.example.week_1.databinding.FragmentMyBinding
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_CONTACTS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        binding.viewPager.apply {
            adapter = MyPagerAdapter(context as FragmentActivity)
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0-> {
                    tab.text = "Contact"
                    binding.textView.text = "Contact"
                    tab.setIcon(R.drawable.ic_baseline_people)
                }
                1-> {
                    tab.text = "Gallery"
                    binding.textView.text = "Gallery"
                    tab.setIcon(R.drawable.ic_baseline_image)}

                2-> {
                    tab.text = "32"
                    binding.textView.text = "4"
                    tab.setIcon(R.drawable.ic_baseline_image)
                }
            }
        }.attach()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() {
        if (!isPermitted()) {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }
        else Timber.i("All permissions are granted")
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


}