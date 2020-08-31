package chapter3.what_is_immutability

fun main() {
    for (i in 1..3) {
        println("Calling ${i}st time ${MutableVal.myString}")
    }
}

object MutableVal {
    var count = 0
    val myString: String = "Mutable"
        get() {
            return "$field ${++count}"
        }
}