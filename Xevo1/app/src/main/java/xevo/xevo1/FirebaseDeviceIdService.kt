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

class FirebaseDeviceIdService : FirebaseInstanceIdService() {
    var userId: String? = null

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        //Uploading token to shared preferences to retrieve from main
        //This is done to account for multiple users using the same device
        //Entry wiped on uninstall or datawipe, in which case new token generated anyway
        val sharedPreferences = application.getSharedPreferences(getString(R.string.fcm),
                Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fcm", refreshedToken)
        editor.commit()

        sendRegistrationToServer(refreshedToken)
    }

    /**
     * Uploads device token to database.
     * Useful if user wiped data, signs in, but token not generated till after
     * call for upload in main.
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId!=null) {
            val ref = FirebaseDatabase.getInstance().getReference(
                    getString(R.string.db_users) + userId + "/device")
            ref.setValue(token)
        }
    }

    companion object {

        private val TAG = "MyFirebaseIIDService"
    }
}

