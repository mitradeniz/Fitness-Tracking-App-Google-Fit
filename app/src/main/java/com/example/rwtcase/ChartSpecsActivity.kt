package com.example.rwtcase

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rwtcase.databinding.ActivityChartSpecsBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.google.android.material.tabs.TabLayout

class ChartSpecsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartSpecsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartSpecsBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val receivedData = intent.getStringExtra("dataKey")

        when (receivedData) {
            "1" -> {
                binding.chartSpecActivitySummaryTextView.text = "Adım:"
                barDataSet = BarDataSet(barEntryListStep, "Adım:")
                barData = BarData(barDataSet)
                barChart = findViewById(R.id.chartSpecActivityBarChart)
                barChart.data = barData
                barDataSet.color = Color.YELLOW
                barDataSet.valueTextColor = Color.BLACK
                barDataSet.valueTextSize = 0.25f
                barChart.description.isEnabled = false
            }
            "2" -> {
                binding.chartSpecActivitySummaryTextView.text = "Kalori:"
                barDataSet = BarDataSet(barEntryListCal, "Kalori:")
                barData = BarData(barDataSet)
                barChart = findViewById(R.id.chartSpecActivityBarChart)
                barChart.data = barData
                barDataSet.color = Color.RED
                barDataSet.valueTextColor = Color.BLACK
                barDataSet.valueTextSize = 0.25f
                barChart.description.isEnabled = false
            }
            "3" -> {
                binding.chartSpecActivitySummaryTextView.text = "Hareket Süresi:"
                barDataSet = BarDataSet(barEntryListMin, "Hareket S.:")
                barData = BarData(barDataSet)
                barChart = findViewById(R.id.chartSpecActivityBarChart)
                barChart.data = barData
                barDataSet.color = Color.BLUE
                barDataSet.valueTextColor = Color.BLACK
                barDataSet.valueTextSize = 0.25f
                barChart.description.isEnabled = false
            }
        }



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> {
                            // Günlük tab'a tıklandığında yapılacak işlemler
                            // Örneğin, günlük fragment'ı yükle
                            Toast.makeText(this@ChartSpecsActivity, "gunluk", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            Toast.makeText(this@ChartSpecsActivity, "haftalık", Toast.LENGTH_SHORT).show()

                            // Haftalık tab'a tıklandığında yapılacak işlemler
                            // Örneğin, haftalık fragment'ı yükle
                        }
                        2 -> {
                            Toast.makeText(this@ChartSpecsActivity, "aylik", Toast.LENGTH_SHORT).show()

                            // Aylık tab'a tıklandığında yapılacak işlemler
                            // Örneğin, aylık fragment'ı yükle
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Herhangi bir tab seçimi kaldırıldığında yapılacak işlemler
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Mevcut tab'a tekrar tıklandığında yapılacak işlemler
            }
        })


    }
}