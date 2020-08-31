package chapter3.what_is_immutability

fun main() {
    for (i in 1..3) {
        println("Calling ${i}st time ${MutableVal.myString}")
    }

    val mutableObj: MutableObj = MutableObj()
    println("MutableObj $mutableObj")
    mutableObj.value = "Changed"
    println("MutableObj $mutableObj")

    val list = mutableListOf("a", "b", "c", "d", "e")
    println(list)
    list.add("f")
    println(list)
}

object MutableVal {
    var count = 0
    val myString: String = "Mutable"
        get() {
            return "$field ${++count}"
        }
}

class MutableObj {
    var value = ""

    override fun toString(): String {
        return "MutableObj(value=$value)"
    }
}