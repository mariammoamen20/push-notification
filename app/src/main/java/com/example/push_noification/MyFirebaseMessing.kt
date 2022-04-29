package com.example.push_noification

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessing : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //show notification
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        var builder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle(message.notification?.title)
                if(message.notification?.imageUrl==null){
                    builder.setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(message.notification?.body))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                }else {
                    val bitmap  = loadImageFromURLToBitmap(message)
                    builder.setStyle(
                        NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                }

        NotificationManagerCompat.from(this).notify(10,builder.build())
    }

    @SuppressLint("CheckResult")
    private fun loadImageFromURLToBitmap(message: RemoteMessage): Bitmap {
        var bitmap : Bitmap?=null
        Glide.with(this)
            .asBitmap()
            .load(message.notification?.imageUrl)
            .addListener(object :RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                   return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    bitmap = resource
                    return true
                }

            })
        return bitmap!!

      }

}