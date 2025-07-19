package org.example.app

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

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
    
    val buffered: BufferedImage
        get() {
            val image = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
            for (y in 0 until h) {
                for (x in 0 until w) {
                    image.setRGB(x, y, colors[index(x, y)].rgb)
                }
            }
            return image
        }
    
    fun save(path: String) {
        ImageIO.write(buffered, "png", java.io.File(path))
    }
}
