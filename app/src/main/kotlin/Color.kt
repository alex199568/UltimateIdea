package org.example.app

data class Color(
    val r: Double,
    val g: Double,
    val b: Double
) {
    constructor(r: Number, g: Number, b: Number) : this(
        r.toDouble(),
        g.toDouble(),
        b.toDouble()
    )

    override fun equals(other: Any?): Boolean {
        val c = other as? Color ?: return false
        return r eq c.r && g eq c.g && b eq c.b
    }

    override fun hashCode(): Int {
        var result = r.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }

    operator fun plus(other: Color): Color {
        return Color(
            r + other.r,
            g + other.g,
            b + other.b
        )
    }

    operator fun minus(other: Color): Color {
        return Color(
            r - other.r,
            g - other.g,
            b - other.b
        )
    }

    operator fun times(n: Number): Color {
        val factor = n.toDouble()
        return Color(
            r * factor,
            g * factor,
            b * factor
        )
    }

    operator fun times(other: Color): Color {
        return Color(
            r * other.r,
            g * other.g,
            b * other.b
        )
    }

    operator fun div(n: Number): Color {
        val factor = n.toDouble()
        require(factor != 0.0) { "Division by zero is not allowed" }
        return Color(
            r / factor,
            g / factor,
            b / factor
        )
    }
}
