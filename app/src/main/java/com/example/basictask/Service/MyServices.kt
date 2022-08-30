package com.example.basictask.Service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.basictask.Constant.INTENT_FILTER
import com.example.basictask.Constant.POCKET
import com.example.basictask.Constant.SHARED_PREFERENCE_STORAGE


class MyServices:Service(), SensorEventListener {
    private lateinit var sensorManeger: SensorManager
    private lateinit var proximitySensor:Sensor
    private lateinit var sharedPreferences: SharedPreferences
    private var counterForPockets=0
    lateinit var sharedPreferencesEdit: SharedPreferences.Editor
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sharedPreferences=getSharedPreferences(SHARED_PREFERENCE_STORAGE, Context.MODE_PRIVATE)
        sharedPreferencesEdit=sharedPreferences.edit()
        sensorManeger=getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor=sensorManeger.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorManeger.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL)
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY){

            if (event.values[0]<=0){
                counterForPockets=sharedPreferences.getInt(POCKET,0)
                counterForPockets++
                sendMessage()
                sharedPreferencesEdit.putInt(POCKET,counterForPockets).commit()
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    private fun sendMessage() {
        val intent = Intent(INTENT_FILTER)
        intent.putExtra(POCKET,counterForPockets)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}