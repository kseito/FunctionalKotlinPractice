package chapter12.logical_complement

import arrow.core.extensions.list.monoidal.identity
import arrow.core.extensions.set.monoidal.identity
import arrow.optics.Lens
import arrow.syntax.function.Partial
import arrow.syntax.function.memoize
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    //Predicate class doesn't exist.
//    val evenPredicate: Predicate<Int> =

    var lambdaFib: (Long) -> Long = { it }
    lambdaFib = { n: Long ->
        if (n < 2) n else lambdaFib(n - 1) + lambdaFib(n - 2)
    }

    var memoizedFib: (Long) -> Long = { it }
    memoizedFib = { n: Long ->
        if (n < 2) n else memoizedFib(n - 1) + memoizedFib(n - 2)
    }.memoize()

//    println(milliseconds("imperative fib") { imperativeFib(40) })
//    println(milliseconds("recursive fib") { recursiveFib(40) })
//    println(milliseconds("lambda fib") { lambdaFib(40) })
//    println(milliseconds("memoized fib") { memoizedFib(40) })

//    val upper: (String?) -> String = { s: String? -> s!!.toString() }
//    listOf("one", "two", null, "four").map(upper).forEach(::println)

    val laptopX8 = Laptop(500.0, MotherBoard("X", Memory(8)))
    val laptopX16 = laptopX8.copy(
            price = 780.0,
            motherBoard = laptopX8.motherBoard.copy(
                    memory = laptopX8.motherBoard.memory.copy(
                            size = laptopX8.motherBoard.memory.size * 2
                    )
            )
    )

    println("laptopX16 = $laptopX16")

    val laptopPrice: Lens<Laptop, Double> = Lens(
            get = { laptop -> laptop.price },
            set = { laptop, price -> laptop.copy(price = price) }
    )

    val laptopMotherBoard: Lens<Laptop, MotherBoard> = Lens(
            get = { laptop -> laptop.motherBoard },
            set = { laptop, mb -> laptop.copy(motherBoard = mb) }
    )

    val motherBoardMemory: Lens<MotherBoard, Memory> = Lens(
            get = { mb -> mb.memory },
            set = { mb, memory -> mb.copy(memory = memory) }
    )

    val memorySize: Lens<Memory, GB> = Lens(
            get = { memory -> memory.size },
            set = { memory, size -> memory.copy(size = size) }
    )

    //cannot compile :-<
//    val laptopMemorySize: Lens<Laptop, GB> = laptopMotherBoard compose motherBoardMemory compose  memorySize
}

inline fun milliseconds(description: String, body: () -> Unit): String {
    return "$description:${measureNanoTime(body)}"
}

fun recursiveFib(n: Long): Long = if (n < 2) {
    n
} else {
    recursiveFib(n - 1) + recursiveFib(n - 2)
}

fun imperativeFib(n: Long): Long {
    return when (n) {
        0L -> 0
        1L -> 1
        else -> {
            var a = 0L
            var b = 1L
            var c = 0L
            for (i in 2..n) {
                c = a + b
                a = b
                b = c
            }
            c
        }
    }
}

typealias GB = Int

data class Memory(val size: GB)
data class MotherBoard(val brand: String, val memory: Memory)
data class Laptop(val price: Double, val motherBoard: MotherBoard)