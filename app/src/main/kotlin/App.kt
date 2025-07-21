package org.example.app

import org.example.app.linalg.Point
import kotlin.time.measureTime

private fun renderSimpleImage() {
    val image = Image(128, 96)

    for (y in 20 until 32) {
        for (x in 30 until 100) {
            image[x, y] = Color.red
        }
    }

    image.save("renders/image.png")
}

fun main() {
    println("Ultimate Idea")

    val rayOrigin = Point(0, 0, -5)
    val wallZ = 10.0
    val wallSize = 7.0
    val pixels = 100
    val pixelSize = wallSize / pixels
    val half = wallSize / 2.0
    val canvas = Image(pixels, pixels)
    val color = Color.red
    val sphere = Sphere()

    val duration = measureTime {
        for (y in 0 until pixels) {
            val worldY = half - pixelSize * y
            for (x in 0 until pixels) {
                val worldX = -half + pixelSize * x
                val position = Point(worldX, worldY, wallZ)
                val r = Ray(rayOrigin, (position - rayOrigin).unit)
                val intersections = sphere.intersect(r)
                if (intersections.isNotEmpty()) {
                    canvas[x, y] = color
                }
            }
        }
    }

    println("Rendering time: $duration")

    canvas.save("renders/sphere.png")
}
