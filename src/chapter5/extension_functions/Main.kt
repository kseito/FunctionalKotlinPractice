package chapter5.extension_functions

import sun.corba.Bridge

fun main() {
    printSpeak(Canine())
    printSpeak(Dog())

    printSpeak(Feline())
    printSpeak(Cat())

    printSpeak(Primate("Koko"))
    printSpeak(GiantApe("Kong"))

    val adam = Caregiver("Adam")
    val fulgencio = Cat()
    val koko = Primate("Koko")
    adam.takeCare(fulgencio)
    adam.takeCare(koko)

    val brenda = Vet("Brenda")
    listOf(adam, brenda).forEach { caregiver ->
        println("${caregiver.javaClass.simpleName} ${caregiver.name}")
        caregiver.takeCare(fulgencio)
        caregiver.takeCare(koko)
    }

    val worker = Worker()
    println(worker.work())
    println(worker.work("refactoring"))
    println(worker.rest())

    println(Builder.buildBridge())
    println(Designer.Companion.fastPrototype())
    Designer.Desk.portfolio().forEach(::println)

    println(1 superOperation 2)
    println(1.superOperation(2))
}

open class Canine {
    open fun speak() = "<generic canine noise>"
}

class Dog : Canine() {
    override fun speak() = "woof!"
}

fun printSpeak(canine: Canine) {
    println(canine.speak())
}

open class Feline

fun Feline.speak() = "<generic feline noise>"

class Cat : Feline()

fun Cat.speak() = "meow!!"

fun printSpeak(feline: Feline) {
    println(feline.speak())
}

open class Primate(val name: String)

fun Primate.speak() = "$name: <generic primate noise>"

open class GiantApe(name: String) : Primate(name)

fun GiantApe.speak() = "${this.name} :<scary 100db roar>"
fun printSpeak(primate: Primate) {
    println(primate.speak())
}

open class Caregiver(val name: String) {
    open fun Feline.react() = "PURRR!!!"

    fun Primate.react() = "$name play with ${this@Caregiver.name}"

    fun takeCare(feline: Feline) {
        println("Feline reacts ${feline.react()}")
    }

    fun takeCare(primate: Primate) {
        println("Primate reacts: ${primate.react()}")
    }
}

class Vet(name: String) : Caregiver(name) {
    override fun Feline.react(): String = "*runs away from $name*"
}

class Worker {
    fun work() = "*working hard*"

    private fun rest() = "*resting*"
}

fun Worker.work() = "*not working so hard*"
fun <T> Worker.work(t: T) = "*working on $t*"
fun Worker.rest() = "*playing video games*"

object Builder {

}

fun Builder.buildBridge() = "A shinny new bridge"

class Designer {
    companion object {

    }

    object Desk {

    }
}

fun Designer.Companion.fastPrototype() = "Prototype"
fun Designer.Desk.portfolio() = listOf("Project1", "Project2")

infix fun Int.superOperation(i: Int) = this + i