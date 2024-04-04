package com.application.tripapp.ui.start

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.application.tripapp.R
import com.application.tripapp.reciever.AirplaneReceiver
import com.application.tripapp.reciever.CallReciever
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val receiver = AirplaneReceiver()
    private val callReceiver = CallReciever()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(
            receiver, IntentFilter(
                Intent.ACTION_AIRPLANE_MODE_CHANGED
            )
        )

        registerReceiver(
            callReceiver, IntentFilter(
               TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            )
    }
}
