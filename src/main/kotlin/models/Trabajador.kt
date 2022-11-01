package models

class Trabajador(val capsula: Capsula) : Runnable {
    override fun run() {
        println(
            """Trabajador: ${Thread.currentThread().name} con la capsula ${capsula.id} con ${capsula.pasajeros} pasajeros
            | Tiempo de lanzamiento programado: ${capsula.tiempoLanzamiento}
            | -----------------------------------------------------------------""".trimMargin()
        )
        Thread.sleep(capsula.tiempoLanzamiento.inWholeMilliseconds)
        println("-- Trabajador: ${Thread.currentThread().name} termino de preparar la capsula ${capsula.id}")
    }
}
