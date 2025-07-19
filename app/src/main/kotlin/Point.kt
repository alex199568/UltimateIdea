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

    override fun equals(other: Any?): Boolean {
        val p = other as? Point ?: return false
        return x eq p.x && y eq p.y && z eq p.z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

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
