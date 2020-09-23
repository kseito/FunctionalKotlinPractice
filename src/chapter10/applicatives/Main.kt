package chapter10.applicatives

import chapter10.functors.Option
import chapter10.monads.flatMap
import chapter10.monads.map

fun main() {
    val numbers = listOf(1, 2, 3)
    val functions = listOf<(Int) -> Int>({ i -> i * 2 }, { i -> i + 3 })
    val result = numbers
            .ap(functions)
            .joinToString()
    println(result)

    val maybeFive = Option.Some(5)
    val maybeTwo = Option.Some(2)
    println(maybeTwo.ap(maybeFive.map { f -> { t: Int -> f + t } }))

    println(Option.pure { f: Int -> { t: Int -> f + t } } `(*)` maybeFive `(*)` maybeTwo)

    val f: (String) -> Int = Function1.pure(0)
    println(f("Hello,"))
    println(f("World"))
    println(f("!"))
}

fun <T, R> List<T>.ap(fab: List<(T) -> R>): List<R> = fab.flatMap { f -> this.map(f) }

fun <T> Option.Companion.pure(t: T): Option<T> = Option.Some(t)

fun <T, R> Option<T>.ap(fab: Option<(T) -> R>): Option<R> = fab.flatMap { f -> map(f) }

infix fun <T, R> Option<(T) -> R>.`(*)`(o: Option<T>): Option<R> = flatMap { f: (T) -> R -> o.map(f) }

object Function1 {
    fun <A, B> pure(b: B) = { _: A -> b }
}

fun <A, B, C> ((A) -> B).map(transform: (B) -> C): (A) -> C = { t -> transform(this(t)) }

fun <A, B, C> ((A) -> B).flatMap(fm: (B) -> (A) -> C): (A) -> C = { t -> fm(this(t))(t) }

fun <A, B, C> ((A) -> B).ap(fab: (A) -> (B) -> C): (A) -> C = fab.flatMap { f -> map(f) }