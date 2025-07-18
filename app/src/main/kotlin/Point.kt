package org.example.app

data class Point(
    val x: Double,
    val y: Double,
    val z: Double
) {
    constructor(x: Number, y: Number, z: Number) : this(
        x.toDouble(),
        y.toDouble(),
        z.toDouble()
    )

    operator fun plus(other: Vector): Point {
        return Point(
            x + other.x,
            y + other.y,
            z + other.z
        )
    }

    operator fun minus(other: Vector): Point {
        return Point(
            x - other.x,
            y - other.y,
            z - other.z
        )
    }

    operator fun minus(other: Point): Vector {
        return Vector(
            x - other.x,
            y - other.y,
            z - other.z
        )
    }
}
