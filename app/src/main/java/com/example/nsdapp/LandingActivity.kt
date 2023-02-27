package com.example.nsdapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        findViewById<MaterialButton>(R.id.start_service_button).setOnClickListener {
            ContextCompat.startForegroundService(applicationContext, Intent(this, DiscoveryService::class.java))
        }
        findViewById<MaterialButton>(R.id.discover_service_button).setOnClickListener {
            startActivity(Intent(this, ClientActivity::class.java))
        }
    }
}