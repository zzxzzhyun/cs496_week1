package com.example.week_1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class introlActivity : AppCompatActivity() {
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS)


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introl)
        checkPermission()
        var handler = Handler()
        handler.postDelayed( {
            var intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
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
}
