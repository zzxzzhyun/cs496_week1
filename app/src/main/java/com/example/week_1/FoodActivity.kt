package com.example.week_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.week_1.databinding.ActivityFoodBinding

class FoodActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_food)

        val restaurant = intent.getStringExtra("name")
        val category = intent.getStringExtra("category")
        val location = intent.getStringExtra("location")
        val lon = intent.getDoubleExtra("lon", 0.0)
        val lat = intent.getDoubleExtra("lat", 0.0)
        val image: Int? = intent.extras?.getInt("Thumbnail")

        val tvres: TextView =findViewById(R.id.txtRes)
        val tvcat: TextView = findViewById(R.id.txtCat)
        val img: ImageView = findViewById(R.id.foodthumbnail)
        val tvloc: TextView = findViewById(R.id.txtLoc)

        tvres.setText(restaurant)
        tvcat.setText(category)
        tvloc.setText(location)

        if (image != null) {
            binding.foodthumbnail.setImageResource(image)
        }

        val button: Button = findViewById(R.id.mapButton)

        button.setOnClickListener (View.OnClickListener(){
            val mIntent =
                Intent(this, MainActivity::class.java).apply {
                putExtra("lat", lat)
                putExtra("lon", lon)
                    putExtra("res", restaurant)
            }
            this.startActivity(mIntent)
//            setResult(RESULT_OK, mIntent)
            if(!isFinishing) finish()
        })

    }
}