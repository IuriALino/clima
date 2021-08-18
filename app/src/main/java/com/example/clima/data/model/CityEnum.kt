package com.example.clima.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CityEnum(val description: String, val country:String) : Parcelable {
    SILVERSTONE ("Silverstone", "Reino Unido"),
    SAO_PAULO ("São Paulo", "Brasil"),
    MELBOURNE ("Melbourne", "Austrália"),
    MONTE_CARLO ("Monte Carlo", "Mônaco"),
}