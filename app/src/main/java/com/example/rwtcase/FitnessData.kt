package com.example.rwtcase

import java.time.LocalDateTime

data class FitnessData(
    val type: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val value: String
)
