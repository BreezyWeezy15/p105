package com.app.uplock

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast


class AppLockService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (isServiceEnabled && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName.toString()
            if (packageName == "com.android.chrome") {
                val intent = Intent(this, LockScreenActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(this, "Chrome is Locked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInterrupt() {
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100
        serviceInfo = info
    }

    companion object {
        var isServiceEnabled: Boolean = false
    }
}