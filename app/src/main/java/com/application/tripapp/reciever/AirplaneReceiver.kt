package com.application.tripapp.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.application.tripapp.R

class AirplaneReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, context?.getString(R.string.airplane_mode), Toast.LENGTH_LONG).show()
    }
}