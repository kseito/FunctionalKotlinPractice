package chapter12.partial_application

import arrow.syntax.function.*

fun main() {
    println(redStrong("Red Sonja", "movie1"))

    val footer: (String) -> String = { content -> "<footer>$content</content>" }

    val fixFooter: () -> String = footer.bind("Functional Kotlin - 2018")

    println(fixFooter)

    println(redStrong.reverse()("movie2", "The Hunt for Red October"))

    "From a pipe".pipe(chapter12.function_composition.strong).pipe(::println)

    val redStrong: (String, String) -> String = "color: red" pipe3 strong.reverse()
    redStrong("movie3", "Three colors: Red") pipe ::println

    val curriedStrong: (style: String) -> (id: String) -> (body: String) -> String = strong.reverse().curried()

    val greenStrong: (id: String) -> (body: String) -> String = curriedStrong("color:green")
    val uncurriedGreenStrong: (id: String, body: String) -> String = greenStrong.uncurried()

    println(greenStrong("movie5")("Green Inferno"))
    println(uncurriedGreenStrong("movie6", "Green Hornet"))
    "Fried Green Tomatoes" pipe ("movie7" pipe greenStrong) pipe ::println
}

val strong: (String, String, String) -> String = { body, id, style ->
    "<strong id=\"$id\" style =\"$style\">$body</strong>"
}

val redStrong: (String, String) -> String = strong.partially3("font:red")

// cannot compile
//val blueStrong: (String, String) -> String = strong(p3 = "font:red")