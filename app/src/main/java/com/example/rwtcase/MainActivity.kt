package com.example.rwtcase

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.rwtcase.databinding.ActivityMainBinding
import com.example.rwtcase.databinding.ActivityPermissionsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

internal lateinit var a: MutableList<FitnessData>
internal var listAll = mutableListOf<FitnessData>()
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingPermissionsBinding: ActivityPermissionsBinding

    private val REQUEST_CODE_ACTIVITY_RECOGNITION = 101
    private val REQUEST_CODE_ACCESS_FINE_LOCATION = 102
    private val REQUEST_CODE_BODY_SENSORS = 103
    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1111

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingPermissionsBinding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainActivityTo2Button.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }


        val permissionLoc = Manifest.permission.ACCESS_FINE_LOCATION // İzin türüne göre değiştirin
        val permissionRec = Manifest.permission.ACTIVITY_RECOGNITION
        val permissionBodySens = Manifest.permission.BODY_SENSORS


/*        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
        gifImageView.setImageResource(R.drawable.loading_screen)

        binding.gifImageView.setImageResource(R.drawable.loading_screen)*/

        if (ContextCompat.checkSelfPermission(this, permissionLoc) == PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(this, permissionRec) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, permissionBodySens) == PackageManager.PERMISSION_GRANTED) {
            // İzin verilmişse burada işlemleri gerçekleştirin
            Toast.makeText(this@MainActivity, "İzine gerek yok!!", Toast.LENGTH_SHORT).show()
        } else {
            // İzin verilmemişse kullanıcıdan izin isteyin

            startActivity(Intent(this@MainActivity, PermissionsActivity::class.java))

        }


        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                account,
                fitnessOptions)
        } else {
            accessGoogleFit()
        }


    }

    private fun requestPermissions(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }

    private fun accessGoogleFit() {
        val end = LocalDateTime.now()
        val start = end.minusYears(1)
        val endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond()
        val startSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond()


        // .aggregate(DataType.TYPE_ACTIVITY_SEGMENT)
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
            .aggregate(DataType.TYPE_MOVE_MINUTES)
            .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()

        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

        Fitness.getHistoryClient(this, account)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    a = dumpDataSet(dataSet)
                    listAll += a
                    for (i in a) {
                        Log.e("1", i.startTime.toString())
                        Log.e("2", i.endTime.toString())
                        Log.e("3", i.type.toString())
                        Log.e("4", i.value.toString())
                    }
                }
                val intent = Intent(this, MainActivity2::class.java)

                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "There was an error reading data from Google Fit", e)
            }

        // Ana aktiviteye geçiş


    }

    private fun dumpDataSet(dataSet: DataSet): MutableList<FitnessData> {
        val fitnessDataList = mutableListOf<FitnessData>()

        //Log.i("TAG", "Data returned for Data type: ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            //Log.e("TAG","Data point:")
            //Log.e("TAG","\tType: ${dp.dataType.name}")

            val startTime = Instant.ofEpochSecond(dp.getStartTime(TimeUnit.SECONDS))
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
            val endTime = Instant.ofEpochSecond(dp.getEndTime(TimeUnit.SECONDS))
                .atZone(ZoneId.systemDefault()).toLocalDateTime()

            //Log.e("TAG","\tStart: ${startTime}")
            //Log.e("TAG","\tEnd: ${endTime}")

            val value = dp.getValue(dp.dataType.fields[0]).toString() // Assume value is string
            fitnessDataList.add(FitnessData(dp.dataType.name, startTime, endTime, value))
        }

        return fitnessDataList
    }


    private fun DataPoint.getStartTimeString() =
        Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()

    private fun DataPoint.getEndTimeString() =
        Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE -> accessGoogleFit()
            }
        }
    }
}
