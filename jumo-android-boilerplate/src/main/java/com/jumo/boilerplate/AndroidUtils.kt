package com.jumo.boilerplate

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.Application
import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.media.AudioManager
import android.net.Uri
import android.os.PowerManager
import android.service.notification.StatusBarNotification
import android.telephony.TelephonyManager
import android.view.WindowManager
import androidx.core.app.NotificationManagerCompat
import com.jumo.ktx.JumoContext
import com.jumo.ktx.systemService
import java.util.Locale

object AndroidUtils {
  @JvmStatic
  val application: Application
    get() = JumoContext.application

  @JvmStatic
  val applicationInfo: ApplicationInfo
    get() = application.applicationInfo

  @JvmStatic
  val packageManager: PackageManager
    get() = application.packageManager

  @JvmStatic
  val packageName: String
    get() = application.packageName

  @JvmStatic
  val packageUri: Uri
    get() = Uri.parse("package:$packageName")

  @JvmStatic
  val playStoreUrl: String
    get() = "https://play.google.com/store/apps/details?id=$packageName"

  @JvmStatic
  val playStoreUri: Uri
    get() = Uri.parse(playStoreUrl)

  @JvmStatic
  val context: Context
    get() = JumoContext.appContext

  @JvmStatic
  var locale: Locale = Locale.getDefault()

  @JvmStatic
  val windowManager: WindowManager
    get() = context.systemService(Context.WINDOW_SERVICE)

  @JvmStatic
  val powerManager: PowerManager
    get() = context.systemService(Context.POWER_SERVICE)

  @JvmStatic
  val activityManager: ActivityManager
    get() = context.systemService(Context.ACTIVITY_SERVICE)

  @JvmStatic
  val audioManager: AudioManager
    get() = context.systemService(Context.AUDIO_SERVICE)

  @JvmStatic
  val telephonyManager: TelephonyManager
    get() = context.systemService(Context.TELEPHONY_SERVICE)

  @JvmStatic
  val notificationManager: NotificationManager
    get() = context.systemService(Context.NOTIFICATION_SERVICE)

  @JvmStatic
  val notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(application)
  val activeNotifications: Array<StatusBarNotification>
    get() = notificationManager.activeNotifications

  @JvmStatic
  val alarmManager: AlarmManager
    get() = context.systemService(Context.ALARM_SERVICE)

  @JvmStatic
  val clipboardManager: ClipboardManager
    get() = context.systemService(Context.CLIPBOARD_SERVICE)

  @JvmStatic
  val keyguardManager: KeyguardManager
    get() = context.systemService(Context.KEYGUARD_SERVICE)
}