package com.example.rwtcase

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.health.connect.client.HealthConnectClient
import com.example.rwtcase.databinding.ActivityMain2Binding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.time.LocalDateTime


internal lateinit var barChart: BarChart
internal lateinit var barData: BarData
internal lateinit var barDataSet: BarDataSet
internal lateinit var barEntryListStep: ArrayList<BarEntry>
internal lateinit var barEntryListCal: ArrayList<BarEntry>
internal lateinit var barEntryListMin: ArrayList<BarEntry>
class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding




    private lateinit var stepCountList: MutableList<Pair<Float, Float>>
    private lateinit var calorieList: MutableList<Pair<Float, Float>>    // Gün bilgisi ve atılan adım tutulabilir
    private lateinit var activeMinutesList: MutableList<Pair<Float, Float>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 click
                    // val intent1 = Intent(this@MainActivity, MainActivity::class.java)
                    // startActivity(intent1)
                    true
                }
                R.id.page_2 -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.page_3 -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.page_4 -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

        stepCountList = mutableListOf()
        calorieList = mutableListOf()
        activeMinutesList = mutableListOf()

        for (i in listAll) {
            when (i.type) {
                "com.google.step_count.delta" -> {
                    var x = i.startTime.dayOfMonth.toFloat()

                    stepCountList.add(Pair(x.toFloat(), i.value.toFloat()))

                }
                "com.google.calories.expended" -> {
                    var x = i.startTime.dayOfMonth.toFloat()

                    calorieList.add(Pair(x.toFloat(), i.value.toFloat()))
                }
                "com.google.active_minutes" -> {
                    var x = i.startTime.dayOfMonth.toFloat()

                    activeMinutesList.add(Pair(x.toFloat(), i.value.toFloat()))
                }
            }
        }

        for ((index, pair) in stepCountList.withIndex()) {
            Log.e("StepCountList", "Entry $index -> Start Time: ${(pair.first)}, Value: ${pair.second}")
        }

        for ((index, pair) in calorieList.withIndex()) {
            Log.e("CalorieList", "Entry $index -> Start Time: ${(pair.first)}, Value: ${pair.second}")
        }

        for ((index, pair) in activeMinutesList.withIndex()) {
            Log.e("ActiveMinutesList", "Entry $index -> Start Time: ${(pair.first)}, Value: ${pair.second}")
        }

        // Grafik 1 için barEntryList oluşturma
        barEntryListStep = ArrayList()
        for (pair in stepCountList) {
            barEntryListStep.add(BarEntry(pair.first, pair.second))
        }
        barDataSet = BarDataSet(barEntryListStep, "Adım:")
        barData = BarData(barDataSet)
        barChart = findViewById(R.id.mainActivityStepBarChart)
        barChart.data = barData
        barDataSet.color = Color.YELLOW
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 0.25f
        barChart.description.isEnabled = false

// Grafik 2 için barEntryList oluşturma
        barEntryListCal = ArrayList()
        for (pair in calorieList) {
            barEntryListCal.add(BarEntry(pair.first, pair.second))
        }
        barDataSet = BarDataSet(barEntryListCal, "Kalori:")
        barData = BarData(barDataSet)
        barChart = findViewById(R.id.mainActivityCalBarChart)
        barChart.data = barData
        barDataSet.color = Color.RED
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 0.25f
        barChart.description.isEnabled = false


// Grafik 3 için barEntryList oluşturma
        barEntryListMin = ArrayList()
        for (pair in activeMinutesList) {
            barEntryListMin.add(BarEntry(pair.first, pair.second))
        }
        barDataSet = BarDataSet(barEntryListMin, "Hareket S.:")
        barData = BarData(barDataSet)
        barChart = findViewById(R.id.mainActivityMinChart)
        barChart.data = barData
        barDataSet.color = Color.BLUE
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 0.25f
        barChart.description.isEnabled = false

        binding.mainActivityStepBarChart.setOnClickListener {

            // liste ile fonksiyonu çağır viewBinding ile sayfayı oluştur
            // tek bir xml iskeleti yeterli olacak
            val intent = Intent(this, ChartSpecsActivity::class.java)
            intent.putExtra("dataKey", "1")
            startActivity(intent)

        }

        binding.mainActivityCalBarChart.setOnClickListener {
            val intent = Intent(this, ChartSpecsActivity::class.java)
            intent.putExtra("dataKey", "2")
            startActivity(intent)
        }

        binding.mainActivityMinChart.setOnClickListener {
            val intent = Intent(this, ChartSpecsActivity::class.java)
            intent.putExtra("dataKey", "3")
            startActivity(intent)
        }


//            Log.e("1", i.startTime.toString())
//            Log.e("2", i.endTime.toString())
//            Log.e("3", i.type.toString())
//            Log.e("4", i.value.toString())

    }

    fun dateFormatter(date: LocalDateTime): String {
        var resultTime = ""

        resultTime += "${date.monthValue}-${date.dayOfMonth.toString()}-${date.year}"

        return resultTime
    }

}


