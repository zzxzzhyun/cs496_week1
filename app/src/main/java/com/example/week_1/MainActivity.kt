package com.example.week_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.week_1.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
                    tab.setIcon(R.drawable.ic_launcher_background)}
                1-> {
                    tab.text = "Gallery"
                    tab.setIcon(R.drawable.ic_launcher_background)}
                2-> {
                    tab.text = "32"
                    tab.setIcon(R.drawable.ic_launcher_background)
                }
            }
        }.attach()
    }
}