package org.example.app

class Image(
    val w: Int,
    val h: Int
) {
    
    private val colors = Array(w * h) { Color.black }
    
    fun index(x: Int, y: Int): Int {
        require(x in 0 until w) { "X coordinate $x out of bounds [0, $w)" }
        require(y in 0 until h) { "Y coordinate $y out of bounds [0, $h)" }
        return x + y * w
    }

    operator fun get(x: Int, y: Int): Color {
        return colors[index(x, y)]
    }

    operator fun set(x: Int, y: Int, c: Color) {
        colors[index(x, y)] = c
    }
}
