package com.example.nsdapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Parcel
import android.os.ResultReceiver
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.button.MaterialButton

class LandingActivity : AppCompatActivity() {

    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        isServiceRunning = isServiceRunning(this, DiscoveryService::class.java)
        // broadcast receiver
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    isServiceRunning = intent.getBooleanExtra("serviceStatus", false)
                    setButtonContent()
                }
            }
        }

        setButtonContent()

        // register broadcast manager
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(receiver, IntentFilter("serviceStatusAction"))

        findViewById<MaterialButton>(R.id.start_service_button).setOnClickListener {
            if (!isServiceRunning) {
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(this, DiscoveryService::class.java)
                )
            } else {
                unRegisterService()
            }
        }

        findViewById<MaterialButton>(R.id.discover_service_button).setOnClickListener {
            startActivity(Intent(this, ClientActivity::class.java))
        }

//        LocalBroadcastManager.getInstance(this)
//            .registerReceiver(tokenPassingReceiver, IntentFilter("NSDServiceData"))
    }

//    private val tokenPassingReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            Toast.makeText(context, "Service succesfully started", Toast.LENGTH_LONG).show()
//            findViewById<MaterialButton>(R.id.start_service_button).text = "Stop Service"
//            isServiceRunning = true
//        }
//    }

    private fun unRegisterService() {
        stopService(Intent(this, DiscoveryService::class.java))
    }

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val services: List<ActivityManager.RunningServiceInfo> =
            activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
            if (runningServiceInfo.service.className == serviceClass.name) {
                return true
            }
        }
        return false
    }

    private fun setButtonContent(){
        if(isServiceRunning){
            findViewById<MaterialButton>(R.id.start_service_button).text = "Stop Service"
        } else {
            findViewById<MaterialButton>(R.id.start_service_button).text = "Start Service"
        }
    }
}

interface ServiceStatusListener : Parcelable {
    fun changeServiceStatus(isServiceRunning: Boolean)
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ServiceStatusListener> =
            object : Parcelable.Creator<ServiceStatusListener> {
                override fun createFromParcel(source: Parcel): ServiceStatusListener {
                    return object : ServiceStatusListener {
                        override fun changeServiceStatus(isServiceRunning: Boolean) {

                        }
                    }
                }

                override fun newArray(size: Int): Array<ServiceStatusListener?> {
                    return arrayOfNulls(size)
                }
            }
    }
}