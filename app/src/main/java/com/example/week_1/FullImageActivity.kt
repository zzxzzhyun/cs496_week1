package com.example.week_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView

class FullImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        val position = intent.getIntExtra("id",0)

        val adapter = ImageAdapter(this)

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource((adapter.images[position]))


    }
}