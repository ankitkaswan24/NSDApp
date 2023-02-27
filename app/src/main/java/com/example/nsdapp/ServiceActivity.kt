package com.example.nsdapp

import android.R.attr
import android.content.ContentValues.TAG
import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.RegistrationListener
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.Socket


class ServiceActivity : AppCompatActivity() {

//    private lateinit var mServiceName: String
//    private lateinit var nsdManager: NsdManager
//    private lateinit var SERVICE_TYPE: String
//    private lateinit var mService: NsdServiceInfo



//    private var SERVICE_NAME = "Server Device"
//    private val SERVICE_TYPE = "_http._tcp."
//    private var mNsdManager: NsdManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

//        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)

//        mNsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager
//        registerService(9000)
    }


    override fun onPause() {
//        mNsdManager?.unregisterService(mRegistrationListener)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
//        if (mNsdManager != null) {
//            registerService(9000)
//        }
    }

    override fun onDestroy() {
//        mNsdManager?.unregisterService(mRegistrationListener)
        super.onDestroy()
    }

//    fun registerService(port: Int) {
//        val serviceInfo = NsdServiceInfo()
//        serviceInfo.serviceName = SERVICE_NAME
//        serviceInfo.serviceType = SERVICE_TYPE
//        serviceInfo.port = port
//        mNsdManager?.registerService(
//            serviceInfo,
//            NsdManager.PROTOCOL_DNS_SD,
//            mRegistrationListener
//        )
//    }
//
//    var mRegistrationListener: RegistrationListener = object : RegistrationListener {
//        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
//            val mServiceName = NsdServiceInfo.serviceName
//            SERVICE_NAME = mServiceName
//            Log.d(TAG, "Registered name : $mServiceName")
//            Log.d(TAG, "Registered specs : ${NsdServiceInfo.host}")
////            receiveData(NsdServiceInfo.host, NsdServiceInfo.port)
//        }
//
//        override fun onRegistrationFailed(
//            serviceInfo: NsdServiceInfo,
//            errorCode: Int
//        ) {}
//
//        override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
//            // Service has been unregistered. This only happens when you
//            // call
//            // NsdManager.unregisterService() and pass in this listener.
//            Log.d(
//                TAG,
//                "Service Unregistered : " + serviceInfo.serviceName
//            )
//        }
//
//        override fun onUnregistrationFailed(
//            serviceInfo: NsdServiceInfo,
//            errorCode: Int
//        ) {}
//    }
//
//    fun receiveData(host: InetAddress, port:Int){
//        try {
//            val socket = Socket(host, port)
//            val inputStream: InputStream = socket.getInputStream()
//            val reader = BufferedReader(InputStreamReader(inputStream))
//            val receivedMessage: String = reader.readLine()
//            Log.e("Ankit", "Message is $receivedMessage")
//        } catch (e: IOException) { }
//    }






//    fun registerService(port: Int) {
//        // Create the NsdServiceInfo object, and populate it.
//        val serviceInfo = NsdServiceInfo().apply {
//            // The name is subject to change based on conflicts
//            // with other services advertised on the same network.
//            serviceName = "NsdChat"
//            serviceType = "_nsdchat._tcp"
//            setPort(port)
//        }
//
//        nsdManager = (getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
//            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
//        }
//    }
//
//    private val registrationListener = object : NsdManager.RegistrationListener {
//
//        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
//            // Save the service name. Android may have changed it in order to
//            // resolve a conflict, so update the name you initially requested
//            // with the name Android actually used.
//            mServiceName = NsdServiceInfo.serviceName
//        }
//
//        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
//            // Registration failed! Put debugging code here to determine why.
//        }
//
//        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
//            // Service has been unregistered. This only happens when you call
//            // NsdManager.unregisterService() and pass in this listener.
//        }
//
//        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
//            // Unregistration failed. Put debugging code here to determine why.
//        }
//    }
//
//    private val resolveListener = object : NsdManager.ResolveListener {
//
//        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
//            // Called when the resolve fails. Use the error code to debug.
//            Log.e(TAG, "Resolve failed: $errorCode")
//        }
//
//        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
//            Log.e(TAG, "Resolve Succeeded. $serviceInfo")
//
//            if (serviceInfo.serviceName == mServiceName) {
//                Log.d(TAG, "Same IP.")
//                return
//            }
//            mService = serviceInfo
//            val port: Int = serviceInfo.port
//            val host: InetAddress = serviceInfo.host
//        }
//    }
//
//    // Instantiate a new DiscoveryListener
//    private val discoveryListener = object : NsdManager.DiscoveryListener {
//
//        // Called as soon as service discovery begins.
//        override fun onDiscoveryStarted(regType: String) {
//            Log.d(TAG, "Service discovery started")
//        }
//
//        override fun onServiceFound(service: NsdServiceInfo) {
//            // A service was found! Do something with it.
//            Log.d(TAG, "Service discovery success$service")
//            when {
//                service.serviceType != SERVICE_TYPE -> // Service type is the string containing the protocol and
//                    // transport layer for this service.
//                    Log.d(TAG, "Unknown Service Type: ${service.serviceType}")
//                service.serviceName == mServiceName -> // The name of the service tells the user what they'd be
//                    // connecting to. It could be "Bob's Chat App".
//                    Log.d(TAG, "Same machine: $mServiceName")
//                service.serviceName.contains("NsdChat") -> nsdManager.resolveService(
//                    service,
//                    resolveListener
//                )
//            }
//        }
//
//        override fun onServiceLost(service: NsdServiceInfo) {
//            // When the network service is no longer available.
//            // Internal bookkeeping code goes here.
//            Log.e(TAG, "service lost: $service")
//        }
//
//        override fun onDiscoveryStopped(serviceType: String) {
//            Log.i(TAG, "Discovery stopped: $serviceType")
//        }
//
//        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
//            Log.e(TAG, "Discovery failed: Error code:$errorCode")
//            nsdManager.stopServiceDiscovery(this)
//        }
//
//        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
//            Log.e(TAG, "Discovery failed: Error code:$errorCode")
//            nsdManager.stopServiceDiscovery(this)
//        }
//    }


    //In your application's Activity

//    override fun onPause() {
//        nsdHelper?.tearDown()
//        super.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        nsdHelper?.apply {
//            registerService(connection.localPort)
//            discoverServices()
//        }
//    }
//
//    override fun onDestroy() {
//        nsdHelper?.tearDown()
//        connection.tearDown()
//        super.onDestroy()
//    }
//
//    // NsdHelper's tearDown method
//    fun tearDown() {
//        nsdManager.apply {
//            unregisterService(registrationListener)
//            stopServiceDiscovery(discoveryListener)
//        }
//    }

}