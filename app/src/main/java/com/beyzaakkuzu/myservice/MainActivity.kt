package com.beyzaakkuzu.myservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var button: Button? = null
    var button2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.buttonStart)
        button2 = findViewById(R.id.buttonStop)

        button!!.setOnClickListener(this)
        button2!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonStart -> {
                startService(Intent(this,MyService::class.java))
            }
            R.id.buttonStop ->{
                stopService(Intent(this, MyService::class.java))
            }

        }
    }
}