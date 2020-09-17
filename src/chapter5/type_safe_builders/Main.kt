package chapter5.type_safe_builders

import java.lang.StringBuilder

fun main() {
    println(joinWithPipe)
}

val joinWithPipe = with(listOf("One", "Two", "Three")) {
    joinToString(separator = "|")
}

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

@DslMarker
annotation class ElementMarker

@ElementMarker
abstract class Part(private val name: String) : Element {
    private val children = arrayListOf<Element>()
    protected val attributes = hashMapOf<String, String>()
    protected fun <T : Element> initElement(element: T, init: T.() -> Unit): T {
        element.init()
        children.add(element)
        return element
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}>\n")
        children.forEach { c -> c.render(builder, indent + "\t") }
        builder.append("$indent</$name>\n")
    }

    private fun renderAttributes(): String = buildString {
        attributes.forEach { attr, value -> append(" $attr=\"$value\"") }
    }

    override fun toString(): String = buildString {
        render(this, "")
    }
}