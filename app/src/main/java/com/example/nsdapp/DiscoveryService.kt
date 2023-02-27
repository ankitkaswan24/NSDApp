package com.example.nsdapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.Socket


class DiscoveryService : Service() {

    private var SERVICE_NAME = "Server Device"
    private val SERVICE_TYPE = "_tms._tcp"
    private var mNsdManager: NsdManager? = null

    override fun onCreate() {
        super.onCreate()
        mNsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager
        registerService(9000)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, ServiceActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "Service")
            .setContentTitle("Discovery Service is running")
            .setContentText("The service will allow other devices to discover this service.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Service",
                "MyApp",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, "Service")
        }

        startForeground(1, notification)
        return START_STICKY
    }

    fun registerService(port: Int) {
        val serviceInfo = NsdServiceInfo()
        serviceInfo.serviceName = SERVICE_NAME
        serviceInfo.serviceType = SERVICE_TYPE
        serviceInfo.port = port
        mNsdManager?.registerService(
            serviceInfo,
            NsdManager.PROTOCOL_DNS_SD,
            mRegistrationListener
        )
    }

    var mRegistrationListener: NsdManager.RegistrationListener = object :
        NsdManager.RegistrationListener {
        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            val mServiceName = NsdServiceInfo.serviceName
            SERVICE_NAME = mServiceName
            Log.d(ContentValues.TAG, "Registered name : $mServiceName")
            Log.d(ContentValues.TAG, "Registered specs : ${NsdServiceInfo.host}")
//            receiveData(NsdServiceInfo.host, NsdServiceInfo.port)
        }

        override fun onRegistrationFailed(
            serviceInfo: NsdServiceInfo,
            errorCode: Int
        ) {}

        override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
            // Service has been unregistered. This only happens when you
            // call
            // NsdManager.unregisterService() and pass in this listener.
            Log.d(
                ContentValues.TAG,
                "Service Unregistered : " + serviceInfo.serviceName
            )
        }

        override fun onUnregistrationFailed(
            serviceInfo: NsdServiceInfo,
            errorCode: Int
        ) {}
    }

//    fun receiveData(host: InetAddress, port:Int){
//        try {
//            val socket = Socket(host, port)
//            val inputStream: InputStream = socket.getInputStream()
//            val reader = BufferedReader(InputStreamReader(inputStream))
//            val receivedMessage: String = reader.readLine()
//            Log.e("Ankit", "Message is $receivedMessage")
//        } catch (e: IOException) { }
//    }
}