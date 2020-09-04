package chapter4.high_order_functions

fun main() {
    println("Called with 4,(it*2): ${performOperationOnEven(4) { it * 2 }}")
    println("Called with 5,(it*2): ${performOperationOnEven(5) { it * 2 }}")

    getAnotherFunction(0)("abc")
    getAnotherFunction(2)("def")
    getAnotherFunction(3)("ghi")
}

fun performOperationOnEven(number: Int, operation: (Int) -> Int): Int {
    return if (number % 2 == 0) {
        operation(number)
    } else {
        number
    }
}

fun getAnotherFunction(n: Int): (String) -> Unit {
    return {
        println("n:$n it:$it")
    }
}