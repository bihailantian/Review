package com.xxm.review.test

fun printE() = { println("E") }

fun main() {
    if (true) println("A")
    if (true) {
        println("B")
    }
    if (true) {
        { println("C") }
    }

    { println("D") }

    printE()

    when {
        true -> {
            println("F")
        }
    }
}