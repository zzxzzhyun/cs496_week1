package com.example.week_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val Restaurant = intent.getStringExtra("Restaurant")
        val Category = intent.getStringExtra("Category")
        val image: Int? = intent.extras?.getInt("Thumbnail")

        val tvres: TextView =findViewById(R.id.txtRes)
        val tvcat: TextView = findViewById(R.id.txtCat)
        val img: ImageView = findViewById(R.id.foodthumbnail)

        tvres.setText(Restaurant)
        tvcat.setText(Category)
        if (image != null) {
            img.setImageResource(image)
        }
    }
}