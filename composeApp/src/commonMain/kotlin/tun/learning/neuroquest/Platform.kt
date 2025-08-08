package tun.learning.neuroquest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform