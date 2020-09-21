package chapter10.functors

fun main() {
    listOf(1, 2, 3)
            .map { i -> i * 2 }
            .map(Int::toString)
            .forEach(::println)

    println(Option.Some("Kotlin").map(String::toUpperCase))
    println(Option.None.map(String::toUpperCase))

    val add3AndMultiplyBy2: (Int) -> Int = { i: Int -> i + 3 }.map { j -> j * 2 }
    println(add3AndMultiplyBy2)
    println(add3AndMultiplyBy2(1))
    println(add3AndMultiplyBy2(2))
}

sealed class Option<out T> {
    object None : Option<Nothing>() {
        override fun toString(): String {
            return "None"
        }
    }

    data class Some<out T>(val value: T) : Option<T>()

    companion object
}

fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> Option.Some(transform(value))
}

fun <A, B, C> ((A) -> B).map(transform: (B) -> C): (A) -> C = { t -> transform(this(t)) }