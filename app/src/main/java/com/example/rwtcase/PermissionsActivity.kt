package com.example.rwtcase

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rwtcase.databinding.ActivityPermissionsBinding

class PermissionsActivity : AppCompatActivity() {
    private val REQUEST_CODE_ACTIVITY_RECOGNITION = 101
    private val REQUEST_CODE_ACCESS_FINE_LOCATION = 102
    private val REQUEST_CODE_BODY_SENSORS = 103

    private lateinit var binding: ActivityPermissionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val permissionLoc = Manifest.permission.ACCESS_FINE_LOCATION // İzin türüne göre değiştirin
        val permissionRec = Manifest.permission.ACTIVITY_RECOGNITION
        val permissionBodySens = Manifest.permission.BODY_SENSORS

        binding.permissionActivityFineLocSwitch.isChecked = (ContextCompat.checkSelfPermission(this, permissionLoc) == PackageManager.PERMISSION_GRANTED)
        binding.permissionActivityRecogSwitch.isChecked = (ContextCompat.checkSelfPermission(this, permissionRec) == PackageManager.PERMISSION_GRANTED)
        binding.permissionActivityBodySensSwitch.isChecked = (ContextCompat.checkSelfPermission(this, permissionBodySens) == PackageManager.PERMISSION_GRANTED)

        binding.permissionActivityRecogSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requestPermissions(
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    REQUEST_CODE_ACTIVITY_RECOGNITION
                )

            } else {
                navigateToAppSettings()
            }

        }

        binding.permissionActivityFineLocSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requestPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    REQUEST_CODE_ACCESS_FINE_LOCATION
                )

            } else {
                navigateToAppSettings()

            }

        }

        binding.permissionActivityBodySensSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requestPermissions(Manifest.permission.BODY_SENSORS, REQUEST_CODE_BODY_SENSORS)

            } else {
                navigateToAppSettings()

            }

        }


    }

    private fun requestPermissions(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }

    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}