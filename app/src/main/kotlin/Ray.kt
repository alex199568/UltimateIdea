package org.example.app

import org.example.app.linalg.Point
import org.example.app.linalg.Vector

data class Ray(
    val origin: Point = Point.zero,
    val direction: Vector = Vector.zero
) {

    operator fun get(t: Number): Point {
        return origin + direction * t
    }
}
