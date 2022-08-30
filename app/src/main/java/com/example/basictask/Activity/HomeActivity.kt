package com.example.basictask.Activity

import android.content.*
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.basictask.BroadCastReceiver.MyBroadcastReceiver
import com.example.basictask.Constant.*
import com.example.basictask.R
import com.example.basictask.Service.MyServices


class HomeActivity : AppCompatActivity() {
    lateinit var tvPlugIn:TextView
    lateinit var tvPlugOut:TextView
    lateinit var tvNumOfPocket:TextView
    lateinit var intentFilter:IntentFilter
    private lateinit var myBroadcastReceiver: MyBroadcastReceiver
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesEdit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvPlugIn=findViewById(R.id.tvDisplayPlugIn)
        tvPlugOut=findViewById(R.id.tvDisplayPlugOut)
        tvNumOfPocket=findViewById(R.id.tvNumOfPockets)
        sharedPreferences=getSharedPreferences(SHARED_PREFERENCE_STORAGE, Context.MODE_PRIVATE)
        sharedPreferencesEdit=sharedPreferences.edit()
        var counterForPlugIn=sharedPreferences.getInt(PLUG_IN,0)
        var counterForPlugOut=sharedPreferences.getInt(PLUG_OUT,0)
        var counterForPockets=sharedPreferences.getInt(POCKET,0)
        tvPlugIn.text="PlugIn: $counterForPlugIn"
        tvPlugOut.text="PlugOut: $counterForPlugOut"
        tvNumOfPocket.text="Pockets: $counterForPockets"
        intentFilter= IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        myBroadcastReceiver= MyBroadcastReceiver(tvPlugIn,tvPlugOut,counterForPlugIn,counterForPlugOut,sharedPreferencesEdit)
        registerReceiver(myBroadcastReceiver, intentFilter)
        startService(Intent(this, MyServices::class.java))
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    counterForPockets=intent.getIntExtra(POCKET,0)
                    tvNumOfPocket.text="Pocket $counterForPockets"
                }
            },IntentFilter(INTENT_FILTER)
        )
    }
}