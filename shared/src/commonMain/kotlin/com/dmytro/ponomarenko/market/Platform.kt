package com.dmytro.ponomarenko.market

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform