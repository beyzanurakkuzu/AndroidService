package com.beyzaakkuzu.myservice

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


class MyService: Service() {
    var number: Int? = 0
    private var mBinder: IBinder = MyBinder()
    var Status = MutableLiveData<Int>()

    companion object{
        var isServiceRunning:Boolean= false
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        show_Notification()
        isServiceRunning= true
    }


    override fun onStart(intent: Intent?, startid: Int) {
        getShow()

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                for(x in 0..100){
                    number=x
                    job()
                }
            }

        }


    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show()
    }
    inner class MyBinder : Binder() {
        // Return this instance of MyService so clients can call public methods
        val service: MyService
            get() =// Return this instance of MyService so clients can call public methods
                this@MyService
    }

    suspend fun job(){
        delay(350)
        Status.postValue(number)

    }
    private fun getShow(): LiveData<Int> {
        if (Status == null) {
            Status = MutableLiveData()
            loadShow()
        }
        return Status as MutableLiveData<Int>
    }
    private fun loadShow() {
        Status!!.value = 0
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun show_Notification() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 1, intent, 0)
        val notification: Notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentText("Heading")
            .setContentTitle("subheading")
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        startForeground(1,notification)
        notificationManager.notify(1, notification)

    }




}