package lazy_evaluation

fun main() {
    val i by lazy {
        println("Lazy evaluation")
        1
    }

    println("befor using i")
    println(i)

    val size = listOf({2 + 1}, {3 * 2}, {1 / 0}, {5 - 4}).size
    println(size)
}