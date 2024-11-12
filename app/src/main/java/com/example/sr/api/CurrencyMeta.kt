package com.example.sr.api

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyMeta(
    val last_updated_at: String
)
