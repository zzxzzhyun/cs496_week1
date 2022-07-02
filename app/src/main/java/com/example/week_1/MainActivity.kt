package com.example.week_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.week_1.databinding.ActivityMainBinding
import com.example.week_1.databinding.FragmentMyBinding
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


}