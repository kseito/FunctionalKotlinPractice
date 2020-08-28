package functional_collections

fun main() {
    val numbers = listOf(1, 2, 3, 4)
    numbers.forEach { i ->
        println("i = $i")
    }

    val numbersTwice = numbers.map { i -> i * 2 }
    numbersTwice.forEach { i ->
        println("i2 = $i")
    }

    println(numbers.sum())
    println(numbers.fold(0) { acc, i -> acc + i })
    println(numbers.reduce { acc, i -> acc * i })
}