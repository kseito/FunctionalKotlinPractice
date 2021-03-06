package chapter12.function_composition

import arrow.syntax.function.andThen
import arrow.syntax.function.compose
import arrow.syntax.function.forwardCompose
import com.sun.org.apache.xpath.internal.operations.Quo
import java.util.*

fun main() {
    val divStrong: (String) -> String = div compose strong

    val spanP: (String) -> String = p forwardCompose span

    val randomStrong: () -> String = randomNames andThen span

    println(divStrong("Hello composition world!"))
    println(spanP("Hello composition world!"))
    println(randomStrong())

    val salesSystem: (Quote) -> Unit = ::calculatePrice andThen ::filterBills forwardCompose ::splitter
    salesSystem(Quote(20.0, "Foo", "Shoes", 1))
    salesSystem(Quote(20.0, "Bar", "Shoes", 200))
    salesSystem(Quote(2000.0, "Foo", "Motorbike", 1))
}

val p: (String) -> String = { body -> "<p>$body</p>" }

val strong: (String) -> String = { body -> "<strong>$body</strong>" }

val span: (String) -> String = { body -> "<span>$body</span>" }

val div: (String) -> String = { body -> "<div>$body</div>" }

val randomNames: () -> String = {
    if (Random().nextInt() % 2 == 0) {
        "foo"
    } else {
        "bar"
    }
}

data class Quote(val value: Double, val client: String, val item: String, val quantity: Int)

data class Bill(val value: Double, val client: String)

data class PickingOrder(val item: String, val quantity: Int)

fun calculatePrice(quote: Quote) = Bill(quote.value * quote.quantity, quote.client) to
        PickingOrder(quote.item, quote.quantity)

fun filterBills(billAndOrder: Pair<Bill, PickingOrder>): Pair<Bill, PickingOrder>? {
    val (bill, _) = billAndOrder
    return if (bill.value >= 100) {
        billAndOrder
    } else {
        null
    }
}

fun warehouse(order: PickingOrder) {
    println("Processing order = $order")
}

fun accounting(bill: Bill) {
    println("processing = $bill")
}

fun splitter(billAndOrder: Pair<Bill, PickingOrder>?) {
    if (billAndOrder != null) {
        warehouse(billAndOrder.second)
        accounting(billAndOrder.first)
    }
}