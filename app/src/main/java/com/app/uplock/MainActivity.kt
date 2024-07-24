package com.app.uplock


import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var toggleButton: ToggleButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        toggleButton = findViewById<ToggleButton>(R.id.toggleButton)

        toggleButton?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!isAccessibilityServiceEnabled()) {
                    Toast.makeText(this@MainActivity, "Please enable the Accessibility Service", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    startActivity(intent)
                    toggleButton?.isChecked = false
                } else {
                    AppLockService.isServiceEnabled = true
                    Toast.makeText(this@MainActivity, "App Lock Service Enabled", Toast.LENGTH_SHORT).show()
                }
            } else {
                AppLockService.isServiceEnabled = false
                Toast.makeText(this@MainActivity, "App Lock Service Disabled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
            .stream().anyMatch { serviceInfo: AccessibilityServiceInfo ->
                serviceInfo.id.contains(packageName)
            }
    }
}