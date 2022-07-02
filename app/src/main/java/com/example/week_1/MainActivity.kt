package com.example.week_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
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
                    tab.setIcon(R.drawable.ic_baseline_people)}
                1-> {
                    tab.text = "Gallery"
                    tab.setIcon(R.drawable.ic_baseline_image)}
                2-> {
                    tab.text = "32"
                    binding.textView.text = "4"
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
                    binding.textView.text = "4"
                    binding.viewPager.isUserInputEnabled = false;
                    }
                }

            }
        })
    }

}