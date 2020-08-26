package `first-class`.and.`higher-order`.functions

fun main(args: Array<String>) {
    println(capitalize("hello world!"))
    println(transform("kotlin", capitalize))
    println(transform("kotlin", ::reverse))
    println(transform("kotlin", MyUtils::doNothing))
    val transformer = Transformer()
    println(transform("kotlin", transformer::upperCased))
    println(transform("kotlin", Transformer.Companion::lowerCased))
    println(transform("kotlin", { str -> str.substring(0..1) }))
    println(transform("kotlin", { it.substring(0..1) }))
    println(transform("kotlin") { it.substring(0..1) })

    val securityCheck = false
    unless(securityCheck) { println("You can`t access this website") }

    useMachine(5, PrintMachine())
    useMachine(5, ::println)
    useMachine(5) { println(it) }
}

val capitalize = object : Function1<String, String> {
    override fun invoke(p1: String): String {
        return p1.capitalize()
    }
}

fun <T> transform(t: T, fn: (T) -> T): T {
    return fn(t)
}

fun reverse(str: String): String {
    return str.reversed()
}

object MyUtils {
    fun doNothing(str: String): String {
        return str
    }
}

class Transformer {
    fun upperCased(str: String): String {
        return str.toUpperCase()
    }

    companion object {
        fun lowerCased(str: String): String {
            return str.toLowerCase()
        }
    }
}

fun unless(condition: Boolean, block: () -> Unit) {
    if (!condition) block()
}

typealias Machine<T> = (T) -> Unit

fun <T> useMachine(t: T, machine: Machine<T>) {
    machine(t)
}

class PrintMachine<T> : Machine<T> {
    override fun invoke(p1: T) {
        println(p1)
    }
}