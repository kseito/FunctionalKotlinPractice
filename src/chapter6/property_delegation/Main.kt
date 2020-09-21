package chapter6.property_delegation

import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    notNullStr = "Initial value"
    println(notNullStr)
//    println(notInit)

    println("Not yet initialised")
    println(myLazyVal)

    myStr = "Change Value"
    myStr = "Change Value again"

    myIntEven = 6
    myIntEven = 3
    println("myIntEven:$myIntEven")

    myCounter = 2
    println("myCounter:$myCounter")
    myCounter = 5
    myCounter = 4
    println("myCounter:$myCounter")
    myCounter++
    myCounter--
    println("myCounter:$myCounter")

    val map1 = mapOf(
            Pair("name", "Reactive Programming in Kotlin"),
            Pair("authors", "Rivu Chakraborty"),
            Pair("pageCount", 400),
            Pair("publicationDate", SimpleDateFormat("yyyy/mm/dd").parse("2017/12/05")),
            Pair("publisher", "Packt")
    )
    val map2 = mapOf(
            "name" to "Kotlin Blueprints",
            "authors" to "Ashish Belagali, Hardik Trivedi, Akshay Chordiya",
            "pageCount" to 250,
            "publicationDate" to SimpleDateFormat("yyyy/mm/dd").parse("2017/12/05"),
            "publisher" to "Packt"
    )

    val book1 = Book(map1)
    val book2 = Book(map2)

    println("Book1 ${book1.name}")
    println("Book2 $book2")

    myEven = 6
    println("myEven:$myEven")
    myEven = 3
    println("myEven:$myEven")
    myEven = 5
    println("myEven:$myEven")
    myEven = 8
    println("myEven:$myEven")

    val person = PersonImpl("Mario Arias")
    person.printName()
    println()
    val user = User(person)
    user.printName()
}

lateinit var notNullStr: String
lateinit var notInit: String

val myLazyVal: String by lazy {
    println("Just initialised")
    "My Lazy Val"
}

var myStr: String by Delegates.observable("<Initial Value>") { property, oldValue, newValue ->
    println("Property `${property.name}` changed value from \"$oldValue\" to \"$newValue\"")
}

var myIntEven: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
    println("${property.name} $oldValue -> $newValue")
    newValue % 2 == 0
}

var myCounter: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
    println("${property.name} $oldValue -> $newValue")
    newValue > oldValue
}

data class Book(val delegate: Map<String, Any?>) {
    val name: String by delegate
    val authors: String by delegate
    val pageCount: Int by delegate
    val publicationDate: Date by delegate
    val publisher: String by delegate
}

var myEven: Int by makeEven(0) { property, oldValue, newValue, wasEven ->
    println("${property.name} $oldValue -> $newValue, Even:$wasEven")
}

abstract class MakeEven(initialValue: Int) : ReadWriteProperty<Any?, Int> {
    private var value: Int = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, newValue: Int) {
        val oldValue = newValue
        val wasEven = newValue % 2 == 0
        if (wasEven) {
            this.value = newValue
        } else {
            this.value = newValue + 1
        }

        afterAssignmentCall(property, oldValue, newValue, wasEven)
    }

    abstract fun afterAssignmentCall(property: KProperty<*>, oldValue: Int, newValue: Int, wasEven: Boolean): Unit
}

inline fun makeEven(initialValue: Int, crossinline onAssignment: (property: KProperty<*>, oldValue: Int, newValue: Int, wasEven: Boolean) -> Unit): ReadWriteProperty<Any?, Int> = object : MakeEven(initialValue) {
    override fun afterAssignmentCall(property: KProperty<*>, oldValue: Int, newValue: Int, wasEven: Boolean) {
        onAssignment(property, oldValue, newValue, wasEven)
    }
}

fun useDelegate(shouldPrint: Boolean) {
    val localDelegate by lazy { "Delegate Used" }
    if (shouldPrint) {
        println(localDelegate)
    }

    println("bye bye")
}

interface Person {
    fun printName()
}

class PersonImpl(val name: String) : Person {
    override fun printName() {
        println(name)
    }
}

class User(val person: Person): Person {
    override fun printName() {
        println("Printing Name:")
        person.printName()
    }
}