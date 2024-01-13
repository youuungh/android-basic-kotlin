package com.example.firebasedatabase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("onNewToken()", "fcm token.........$token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("onMessageReceived()", "fcm message..........${message.notification}")
        Log.d("onMessageReceived()", "fcm message..........${message.data}")
    }
}