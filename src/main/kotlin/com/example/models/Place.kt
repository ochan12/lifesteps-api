package com.example.models

import java.util.Locale.IsoCountryCode

data class Place(val name: String, val position: GeoPosition, val countryCode: IsoCountryCode)

data class GeoPosition(val lat: Long, val lon: Long)