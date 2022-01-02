package com.beyzaakkuzu.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


class MyService: Service() {

    override fun onCreate() {


    }

    override fun onStart(intent: Intent?, startid: Int) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show()
    }

}