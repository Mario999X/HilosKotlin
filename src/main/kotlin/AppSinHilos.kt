import models.Capsula
import kotlin.system.measureTimeMillis

// Voy a replicar el ejercicio 4 de procesos, pero esta vez sera con una pool de hilos y sin procesos.
// Este es el caso sin hilos

private const val NUM_CAPSULAS = 5

private var contadorID: Int = 0

fun main() {

    val tiempoEjecucion = measureTimeMillis {
        initMision()
    }
    println("Mision completada en $tiempoEjecucion ms")

}

private fun initMision() {

    println("Preparando zonas de lanzamiento | Numero de Capsulas: $NUM_CAPSULAS")
    val capsulas = produceCapsulas()

    capsulas.forEach {
        println("Capsula: ${it.id} | Pasajeros: ${it.pasajeros}")
        println("Lanzando capsula, tiempo estimado: ${it.tiempoLanzamiento} ")
        Thread.sleep(it.tiempoLanzamiento.inWholeMilliseconds)
    }

}

private fun produceCapsulas(): ArrayList<Capsula> {
    val listadoCapsulaSinHilos = ArrayList<Capsula>()

    for (i in 0 until NUM_CAPSULAS) {
        contadorID += 1
        listadoCapsulaSinHilos.add(Capsula(contadorID))
        //println(listadoCapsulas[i])
    }
    return listadoCapsulaSinHilos
}