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

    override fun equals(other: Any?): Boolean {
        val v = other as? Vector ?: return false
        return x eq v.x && y eq v.y && z eq v.z
    }

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
    
    operator fun unaryMinus(): Vector {
        return Vector(-x, -y, -z)
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
    
    infix fun dot(other: Vector): Double {
        return x * other.x + y * other.y + z * other.z
    }
    
    infix fun cross(other: Vector): Vector {
        return Vector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        )
    }
}