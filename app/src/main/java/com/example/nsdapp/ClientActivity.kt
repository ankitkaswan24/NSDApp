package com.example.nsdapp

import android.content.ContentValues.TAG
import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.InetAddress

class ClientActivity : AppCompatActivity() {

    private val SERVICE_NAME = "Client Device"
    private val SERVICE_TYPE = "_tms._tcp"

    private lateinit var hostAddress: InetAddress
    private var hostPort = 0
    private var mNsdManager: NsdManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        // NSD Stuff
        mNsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager
        mNsdManager?.discoverServices(
            SERVICE_TYPE,
            NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener
        );
    }

    override fun onPause() {
        try {
            mNsdManager?.stopServiceDiscovery(mDiscoveryListener)
        } catch (_: Exception) {

        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        try{
        mNsdManager?.discoverServices(
            SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener
        )
        } catch (_: Exception){

        }
    }

    override fun onDestroy() {
        try {
            mNsdManager?.stopServiceDiscovery(mDiscoveryListener)
        } catch (_: Exception) {

        }
        super.onDestroy()
    }

    var mDiscoveryListener: DiscoveryListener = object : DiscoveryListener {
        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success : $service")
            Log.d(TAG, "Host = " + service.serviceName)
            Log.d(TAG, "port = " + service.port.toString())

            runOnUiThread {
                findViewById<TextView>(R.id.service_tv).text = service.serviceName
            }

            if (service.serviceType != SERVICE_TYPE) {
                // Service type is the string containing the protocol and
                // transport layer for this service.
                Log.d(TAG, "Unknown Service Type: " + service.serviceType)
            } else if (service.serviceName == SERVICE_NAME) {
                // The name of the service tells the user what they'd be
                Log.d(TAG, "Same machine: $SERVICE_NAME")
            } else {
                Log.d(TAG, "Diff Machine : " + service.serviceName)
                // connect to the service and obtain serviceInfo
                mNsdManager?.resolveService(service, mResolveListener)
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            Log.e(TAG, "service lost$service")
            runOnUiThread {
                findViewById<TextView>(R.id.service_tv).text = "service lost${service.serviceName}"
            }
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            mNsdManager?.stopServiceDiscovery(this)
            runOnUiThread {
                findViewById<TextView>(R.id.service_tv).text = "Discovery failed: Error code:$errorCode"
            }
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            mNsdManager?.stopServiceDiscovery(this)
        }
    }

    var mResolveListener: NsdManager.ResolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed $errorCode")
            Log.e(TAG, "serivce = $serviceInfo")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")
            if (serviceInfo.serviceName == SERVICE_NAME) {
                Log.d(TAG, "Same IP.")
                return
            }

            // Obtain port and IP
            hostPort = serviceInfo.port
            hostAddress = serviceInfo.host

//            sendData(hostAddress, hostPort)
        }
    }

//    fun sendData(host:InetAddress, port:Int){
//        var message = "Hello, world!"
//        var messageBytes: ByteArray = message.toByteArray()
//
//        try {
//            val socket = Socket(host, port)
//            val outputStream: OutputStream = socket.getOutputStream()
//            outputStream.write(messageBytes)
//            outputStream.flush()
//        } catch (e: IOException) {
//            // handle the exception
//        }
//    }
}