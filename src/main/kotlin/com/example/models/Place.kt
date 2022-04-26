package com.example.models

import kotlinx.serialization.Serializable
import java.util.Locale.IsoCountryCode

@Serializable
data class Place(val name: String, val position: GeoPosition, val countryCode: String)

@Serializable
data class GeoPosition(val lat: Long, val lon: Long)