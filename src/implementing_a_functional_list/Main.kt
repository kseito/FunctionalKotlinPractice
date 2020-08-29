package implementing_a_functional_list

import implementing_a_functional_list.FunList.*

fun main() {
    val numbers = intListOf(1, 2, 3, 4)
    numbers.forEach { i ->
        println("i = $i")
    }

    val sum = numbers.fold(0) { r, i -> r + i }
    println(sum)

    numbers.map { i -> "i = $i" }.forEach { println(it) }
}

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()

    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()

    fun forEach(f: (T) -> Unit) {
        tailrec fun go(list: FunList<T>, f: (T) -> Unit) {
            when (list) {
                is Cons -> {
                    f(list.head)
                    go(list.tail, f)
                }
                is Nil -> Unit
            }
        }
        go(this, f)
    }

    fun <R> fold(init: R, f: (R, T) -> R): R {
        tailrec fun go(list: FunList<T>, init: R, f: (R, T) -> R): R {
            return when (list) {
                is Cons -> {
                    go(list.tail, f(init, list.head), f)
                }
                is Nil -> init
            }
        }
        return go(this, init, f)
    }

    fun reverse(): FunList<T> = fold(Nil as FunList<T>) { acc, i ->
        Cons(i, acc)
    }

    fun <R> foldRight(init: R, f: (R, T) -> R): R {
        return this.reverse().fold(init, f)
    }

    fun <R> map(f: (T) -> R): FunList<R> {
        return foldRight(Nil as FunList<R>) { tail, head ->
            Cons(f(head), tail)
        }
    }
}

fun intListOf(vararg numbers: Int): FunList<Int> {
    return if (numbers.isEmpty()) {
        Nil
    } else {
        Cons(numbers.first(), intListOf(*numbers.drop(1).toTypedArray().toIntArray()))
    }
}