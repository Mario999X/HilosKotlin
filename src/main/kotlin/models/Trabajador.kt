package models

import java.util.concurrent.Callable

class Trabajador(private val capsula: Capsula) : Runnable, Callable<Int> {
    override fun run() {
        println(
            """Trabajador: ${Thread.currentThread().name} con la capsula ${capsula.id} con ${capsula.pasajeros} pasajeros
            | Tiempo de lanzamiento programado: ${capsula.tiempoLanzamiento}
            | -----------------------------------------------------------------""".trimMargin()
        )
        Thread.sleep(capsula.tiempoLanzamiento.inWholeMilliseconds)
        println("-- Trabajador: ${Thread.currentThread().name} termino de preparar la capsula ${capsula.id}")
    }

    override fun call(): Int {
        var pasajerosSalvados = 0
        println(
            """Trabajador: ${Thread.currentThread().name} con la capsula ${capsula.id} con ${capsula.pasajeros} pasajeros
            | Tiempo de lanzamiento programado: ${capsula.tiempoLanzamiento}
            | -----------------------------------------------------------------""".trimMargin())
        pasajerosSalvados += capsula.pasajeros
        Thread.sleep(capsula.tiempoLanzamiento.inWholeMilliseconds)
        println("-- Trabajador: ${Thread.currentThread().name} termino de preparar la capsula ${capsula.id}")
        return pasajerosSalvados
    }
}
