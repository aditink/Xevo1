package xevo.xevo1

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.support.v4.app.NotificationCompat


class NotificationService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"
    private val FOLLOWER_CHANNEL = "FOLLOWER_CHANNEL"
    private var caseId : String? = null

    /**
     * Called when notification
     * is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage!!.from)
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)
        caseId = remoteMessage.data?.get("caseId")
        Log.d(TAG, "Notification Message caseId: " + caseId)
        sendNotification(remoteMessage)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this@NotificationService, AnswerReady::class.java)
        intent.putExtra("caseId", caseId)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //if 26 or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val followersChannel = NotificationChannel(
                    FOLLOWER_CHANNEL,
                    "Follower Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)

// Configure the channel's initial settings
            followersChannel.lightColor = Color.GREEN
            followersChannel.vibrationPattern = longArrayOf(100, 100)

// Submit the notification channel object to the notification manager
            notificationManager.createNotificationChannel(followersChannel)

            val notificationBuilder = NotificationCompat.Builder(this, FOLLOWER_CHANNEL)
                    .setContentText(remoteMessage.notification?.body)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.xevo_logo)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }
        else {
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentText(remoteMessage.notification?.body)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.xevo_logo)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }
    }
}
