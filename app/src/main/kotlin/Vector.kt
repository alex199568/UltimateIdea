package org.example.app

data class Vector(
    val x: Double,
    val y: Double,
    val z: Double
) {
    // Constructor with Number and conversions
    constructor(x: Number, y: Number, z: Number) : this(
        x.toDouble(),
        y.toDouble(),
        z.toDouble()
    )

    // Operator for addition
    operator fun plus(other: Vector): Vector {
        return Vector(
            x + other.x,
            y + other.y,
            z + other.z
        )
    }

    // Operator for subtraction
    operator fun minus(other: Vector): Vector {
        return Vector(
            x - other.x,
            y - other.y,
            z - other.z
        )
    }

    // Operator for scalar multiplication
    operator fun times(scalar: Number): Vector {
        val factor = scalar.toDouble()
        return Vector(
            x * factor,
            y * factor,
            z * factor
        )
    }

    // Operator for scalar division
    operator fun div(scalar: Number): Vector {
        val factor = scalar.toDouble()
        require(factor != 0.0) { "Division by zero is not allowed" }
        return Vector(
            x / factor,
            y / factor,
            z / factor
        )
    }
}