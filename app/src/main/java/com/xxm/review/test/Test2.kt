package com.xxm.review.test

fun main() {
    var age = 1
    val p = People(age)
    println(p)
    age = 2
    println(p)
}

data class People(var age: Int)