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
    
    val rgb: Int
        get() {
            val ri = (r.coerceIn(0.0, 1.0) * 255).toInt()
            val gi = (g.coerceIn(0.0, 1.0) * 255).toInt()
            val bi = (b.coerceIn(0.0, 1.0) * 255).toInt()
            return (0xFF shl 24) or (ri shl 16) or (gi shl 8) or bi
        }

    companion object {
        val white = Color(1, 1, 1)
        val lightGray = Color(0.75, 0.75, 0.75)
        val gray = Color(0.5, 0.5, 0.5)
        val darkGray = Color(0.25, 0.25, 0.25)
        val black = Color(0, 0, 0)

        val red = Color(1, 0, 0)
        val green = Color(0, 1, 0)
        val blue = Color(0, 0, 1)

        val yellow = Color(1, 1, 0)
        val magenta = Color(1, 0, 1)
        val cyan = Color(0, 1, 1)

        val orange = Color(1, 0.5, 0)
        val purple = Color(0.5, 0, 0.5)
        val brown = Color(0.6, 0.3, 0)
        val pink = Color(1, 0.75, 0.8)

        val lime = Color(0.75, 1, 0)
        val teal = Color(0, 0.5, 0.5)
        val indigo = Color(0.29, 0, 0.51)
        val maroon = Color(0.5, 0, 0)
        val navy = Color(0, 0, 0.5)
        val olive = Color(0.5, 0.5, 0)
        val turquoise = Color(0.25, 0.88, 0.82)
        val violet = Color(0.93, 0.51, 0.93)
        val gold = Color(1, 0.84, 0)
    }
}
