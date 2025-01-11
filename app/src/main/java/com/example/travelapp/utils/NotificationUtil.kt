package com.example.travelapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun hasPostNotificationPermission(context: Context) : Boolean{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
    return true
}

fun shouldShowPostNotificationRequestPermissionRationale(context: Context) : Boolean{
    return ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,Manifest.permission.POST_NOTIFICATIONS)
}