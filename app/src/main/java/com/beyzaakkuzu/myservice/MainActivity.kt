package com.beyzaakkuzu.myservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var mIsBound: Boolean = false
    var mService: MyService? = null

    lateinit var progressBar: ProgressBar

    var button: Button? = null
    var button2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.buttonStart)
        button2 = findViewById(R.id.buttonStop)

        progressBar = findViewById(R.id.progressBar)
        button!!.setOnClickListener(this)
        button2!!.setOnClickListener(this)

       if(MyService.isServiceRunning) {
           bindService(Intent(this,MyService::class.java),serviceConnection, Context.BIND_AUTO_CREATE )

       }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonStart -> {
                startService(Intent(this,MyService::class.java))
                bindService(Intent(this,MyService::class.java),serviceConnection, Context.BIND_AUTO_CREATE )
            }
            R.id.buttonStop ->{
                stopService(Intent(this, MyService::class.java))
                unbindService(serviceConnection)
                mService!!.stopTheProgress()
            }

        }
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder = iBinder as MyService.MyBinder
            mService = binder.service
            mIsBound = true
            observer()


        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mIsBound = false
            Log.println(Log.ASSERT, "ONSERVICE DISCONNECTED", "it")
        }
    }
    fun observer(){
        mService!!.Status.observe(this,{
            progressBar.setProgress(it)

        })
    }

}