fun main(args: Array<String>) {
    println(capitalize("hello world!"))
}

val capitalize = object: Function1<String, String> {
    override fun invoke(p1: String): String {
        return p1.capitalize()
    }
}