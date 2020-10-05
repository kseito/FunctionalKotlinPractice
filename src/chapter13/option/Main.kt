package chapter13.option

import arrow.optics.Optional

fun main() {

}

fun divide(num: Int, den: Int): Int? {
    return if (num % den != 0) {
        null
    } else {
        num / den
    }
}

fun division(a: Int, b: Int, den: Int): Pair<Int, Int>? {
    val aDiv = divide(a, den)
    return when (aDiv) {
        is Int -> {
            val bDiv = divide(b, den)
            when (bDiv) {
                is Int -> aDiv to bDiv
                else -> null
            }
        }
        else -> null
    }
}

fun optionDivide(num: Int, den: Int): Option<Int, Int> = Some