package chapter5.recursion_and_corecursion

fun main() {
    val strings = elements("Kotlin", 5)
    strings.forEach(::println)

    val factorial = factorial(10)
    factorial.forEach(::println)

    val fib = fib(4)
    fib.forEach(::println)
}

fun <T, S> unfold(s: S, f: (S) -> Pair<T, S>?): Sequence<T> {
    val result = f(s)
    return if (result != null) {
        sequenceOf(result.first) + unfold(result.second, f)
    } else {
        sequenceOf()
    }
}

fun <T> elements(element: T, numOfValues: Int): Sequence<T> {
    return unfold(1) { i ->
        if (numOfValues > i) element to i + 1
        else null
    }
}

fun factorial(size: Int): Sequence<Long> {
    return sequenceOf(1L) + unfold(1L to 1) { (acc, n) ->
        if (size > n) {
            val x = n * acc
            (x) to (x to n + 1)
        } else null
    }
}

fun fib(size: Int): Sequence<Long> {
    return sequenceOf(1L) + unfold(Triple(0L, 1L, 1)) { (cur, next, n) ->
        if (size > n) {
            val x = cur + next
            (x) to Triple(next, x, n + 1)
        } else null
    }
}