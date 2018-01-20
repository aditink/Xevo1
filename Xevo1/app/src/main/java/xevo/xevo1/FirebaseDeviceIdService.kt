package xevo.xevo1

import android.app.Service
import android.content.Intent
import android.content.Intent.getIntent
import android.os.IBinder
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import android.widget.Toast
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences





/**
 * Created by aditi on 1/14/18.
 */

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    lateinit var userId: String

//    override fun onCreate() {
//        super.onCreate()
//        onTokenRefresh()
//    }
//
//    override fun onStart(intent: Intent?, startId: Int) {
//        super.onStart(intent, startId)
//        userId = intent!!.getStringExtra("userId")
//        onTokenRefresh()
//    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        val sharedPreferences = application.getSharedPreferences(getString(R.string.fcm),
                Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fcm", refreshedToken)
        editor.commit()

        sendRegistrationToServer(refreshedToken)
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {

//        val ref = FirebaseDatabase.getInstance().getReference(
//                getString(R.string.db_users) + userId + "/device")
//        ref.setValue(token)
        // TODO: Implement this method to send token to your app server.
    }

    companion object {

        private val TAG = "MyFirebaseIIDService"
    }
}

