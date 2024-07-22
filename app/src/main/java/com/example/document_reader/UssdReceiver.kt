

package com.example.document_reader
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast

class USSDReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.NEW_OUTGOING_CALL") {
            val phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            if (phoneNumber == "*123456789#") {
                resultData = null

                val launchIntent = context?.packageManager?.getLaunchIntentForPackage("com.example.document_reader")
                launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(launchIntent)
                Toast.makeText(context, "Opening App", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
