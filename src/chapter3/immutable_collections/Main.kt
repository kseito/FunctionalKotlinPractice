package chapter3.immutable_collections

fun main() {
    val immutableList = listOf(1,2,3,4,5,6,7)
    println("Immtable List $immutableList")
    val mutableList = immutableList.toMutableList()

    println("Mutable List $mutableList")
    mutableList.add(8)
    println("Mutable List after add $mutableList")
    println("Mutable List after add $immutableList")
}