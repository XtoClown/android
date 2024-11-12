package com.example.sr.api

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyJSON(
    val meta: CurrencyMeta,
    val data: Map<String, CurrencyValue>
)
