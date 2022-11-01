import models.Capsula
import models.Trabajador
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

// Este es el caso usando una Pool de hilos
private const val NUM_CAPSULAS = 5
private const val NUM_HILOS = 3

private var contadorID: Int = 0

/*
La version sin hilos es bien simple, de forma secuencial no hay muchas preocupaciones y no se usa la clase Trabajador

En este caso, y siguiendo el tipìco caso de las cajeras, he diseñado a los trabajadores, que implementan Runnable Y Callable

    --- RUNNABLE ---
Usando la lista de capsulas generada en el metodo produceCapsulas, generamos una pool de hilos fija con Executors
que se encargaran de generar un Trabajador que se encargue de la capsula correspondiente

Y aunque el tiempo mostrado sea incorrecto, se puede observar que el tiempo de ejecucion se redujo a practicamente
la mitad (pool de 3 hilos)

    --- CALLABLE ---
En este caso, voy a devolver la suma total de pasajeros que hayan tenido la suerte de escapar en las capsulas
Creamos una lista mutable de Trabajadores que contegan el numero de capsulas ya creadas, usando un forEach

Genereamos una variable que use el metodo invokeAll, pillando la lista de Trabajadores, luego, iremos obteniendo
con un .get el resultado del metodo call de la clase Trabajador, y lo iremos sumando a la variable generada e
inicializada a 0 (en este caso por ser un Int lo que devuelve)

Importante, una vez hayamos procesado toda la lista de capsulas el uso de shutdown, ya que si no la run no termina nunca
*/
fun main() {
    //initMisionRunnable()
    initMisionCallable()
}

private fun initMisionRunnable() {

    println("Preparando zonas de lanzamiento -- Numero de trabajadores: $NUM_HILOS | Numero de Capsulas: $NUM_CAPSULAS")
    val capsulas = produceCapsulas()

    val executor = Executors.newFixedThreadPool(NUM_HILOS)

    measureTimeMillis {
        capsulas.forEach {
            executor.execute(Trabajador(it))
        }
        executor.shutdown()
    }.also {
        println("Mision completada en $it ms") // No entiendo por que no funciona
    }

}

private fun initMisionCallable() {
    println("Preparando zonas de lanzamiento -- Numero de trabajadores: $NUM_HILOS | Numero de Capsulas: $NUM_CAPSULAS")
    val capsulas = produceCapsulas()
    val executor = Executors.newFixedThreadPool(NUM_HILOS)

    var pasajerosSalvados = 0

    measureTimeMillis {
        val trabajadores = mutableListOf<Trabajador>()
        capsulas.forEach {
            trabajadores.add(Trabajador((it)))
        }

        val futures = executor.invokeAll(trabajadores)
        futures.forEach { future ->
            pasajerosSalvados += future.get()
        }
        executor.shutdown()
    }.also {
        println("Pasajeros en capsulas: $pasajerosSalvados")
        println("Mision completada en $it ms") // Aqui funciona por el uso de futures, imagino
    }
}

private fun produceCapsulas(): ArrayList<Capsula> {
    val listadoCapsulas = ArrayList<Capsula>()

    for (i in 0 until NUM_CAPSULAS) {
        contadorID += 1
        listadoCapsulas.add(Capsula(contadorID))
        //println(listadoCapsulas[i])
    }
    return listadoCapsulas
}