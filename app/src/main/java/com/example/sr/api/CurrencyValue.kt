package com.example.sr.api

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyValue(
    val code: String,
    val value: Double
)
