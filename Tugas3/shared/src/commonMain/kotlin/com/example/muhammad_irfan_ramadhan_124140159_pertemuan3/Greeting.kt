package com.example.muhammad_irfan_ramadhan_124140159_pertemuan3

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}