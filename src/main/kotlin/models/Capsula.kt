package models

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class Capsula(
    val id: Int,
    val pasajeros: Int =  (15..50).random(),
    val tiempoLanzamiento: Duration =  (5..10).random().seconds
)
