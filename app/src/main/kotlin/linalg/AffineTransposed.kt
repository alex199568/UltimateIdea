package org.example.app.linalg

class AffineTransposed(
    vararg elements: Number = emptyArray<Number>()
) : Matrix(4, 3, *elements) {

    operator fun times(p: Point): Point {
        val x = this[0, 0] * p.x + this[0, 1] * p.y + this[0, 2] * p.z
        val y = this[1, 0] * p.x + this[1, 1] * p.y + this[1, 2] * p.z
        val z = this[2, 0] * p.x + this[2, 1] * p.y + this[2, 2] * p.z
        val w = this[3, 0] * p.x + this[3, 1] * p.y + this[3, 2] * p.z + 1.0

        // Validate w is non-zero to avoid division errors
        require(w != 0.0) {
            "Invalid transformation: w-component is zero, cannot normalize"
        }

        return Point(x / w, y / w, z / w)
    }

    operator fun times(v: Vector): Vector {
        return Vector(
            this[0, 0] * v.x + this[0, 1] * v.y + this[0, 2] * v.z,
            this[1, 0] * v.x + this[1, 1] * v.y + this[1, 2] * v.z,
            this[2, 0] * v.x + this[2, 1] * v.y + this[2, 2] * v.z
        )
    }
}
