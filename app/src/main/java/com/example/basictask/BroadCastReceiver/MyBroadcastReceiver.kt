package com.example.basictask.BroadCastReceiver

import android.content.*
import android.widget.TextView
import com.example.basictask.Constant.PLUG_IN
import com.example.basictask.Constant.PLUG_OUT

class MyBroadcastReceiver(private val tvPlugIn:TextView,
                          private val tvPlugOut:TextView,
                          private var counterForPlugIn:Int,
                          private var counterForPlugOut:Int,
                          private val editor: SharedPreferences.Editor): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var action=intent?.action
        if (action.equals(Intent.ACTION_POWER_CONNECTED)){
            counterForPlugIn++
            tvPlugIn.text="Plugin: $counterForPlugIn"
            editor.putInt(PLUG_IN,counterForPlugIn).commit()
        }
        else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)){
            counterForPlugOut++
            tvPlugOut.text="Plugout $counterForPlugOut"
            editor.putInt(PLUG_OUT,counterForPlugOut).commit()
        }
    }
}