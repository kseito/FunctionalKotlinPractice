package chapter10.monads

import chapter10.functors.Option

fun main() {
    val numbers = listOf(1, 2, 3)
    val functions = listOf<(Int) -> Int>({ i -> i * 2 }, { i -> i + 3 })
    val result = numbers.flatMap { number ->
        functions.map { f -> f(number) }
    }.joinToString()
    println(result)

    println(calculateDiscount(Option.Some(80.0)))
    println(calculateDiscount(Option.Some(30.0)))
    println(calculateDiscount(Option.None))

    val maybeFive = Option.Some(5)
    val maybeTwo = Option.Some(2)
    println(maybeFive.flatMap { f ->
        maybeTwo.flatMap { t ->
            Option.Some(f + t)
        }
                .map { mixed -> Option.Some(mixed * mixed) }
    })

    println(maybeFive.flatMap { f ->
        maybeTwo.map { t -> f + t }
    })
}

fun <T, R> Option<T>.flatMap(fm: (T) -> Option<R>): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> fm(value)
}

fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = flatMap { t -> Option.Some(transform(t)) }

fun calculateDiscount(price: Option<Double>): Option<Double> {
    return price.flatMap { p ->
        if (p > 50.0) {
            Option.Some(5.0)
        } else {
            Option.None
        }
    }
}