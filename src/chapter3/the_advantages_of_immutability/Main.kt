package chapter3.the_advantages_of_immutability

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    val myData = MyDataImmutable()

    runBlocking {
        async {
            var someDataCopy = myData.someData
            for(i in 11..20) {
                someDataCopy+=i
                println("someData from 1st async $someDataCopy")
                delay(500)
            }
        }

        async {
            var someDataCopy = myData.someData
            for(i in 1..10) {
                someDataCopy++
                println("someData from 2nd async $someDataCopy")
                delay(300)
            }

        }

        runBlocking { delay(10000) }
    }
}

class MyDataImmutable {
    val someData: Int = 0
}