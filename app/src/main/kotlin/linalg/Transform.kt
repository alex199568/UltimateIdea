package org.example.app.linalg

class Transform(
    vararg elements: Number = emptyArray<Number>()
) : SquareMatrix(4, *elements) {

    operator fun times(p: Point): Point {
        val x = this[0, 0] * p.x + this[0, 1] * p.y + this[0, 2] * p.z + this[0, 3]
        val y = this[1, 0] * p.x + this[1, 1] * p.y + this[1, 2] * p.z + this[1, 3]
        val z = this[2, 0] * p.x + this[2, 1] * p.y + this[2, 2] * p.z + this[2, 3]
        val w = this[3, 0] * p.x + this[3, 1] * p.y + this[3, 2] * p.z + this[3, 3]

        // Validate w is non-zero to avoid division errors
        require(w != 0.0) {
            "Invalid transformation: w-component is zero, cannot normalize"
        }

        return Point(x / w, y / w, z / w)
    }

    operator fun times(v: Vector): Vector {
        require(columns == 4 && rows == 4) {
            "Matrix must be 4x4 for vector multiplication, got ${rows}x${columns}"
        }

        return Vector(
            this[0, 0] * v.x + this[0, 1] * v.y + this[0, 2] * v.z,
            this[1, 0] * v.x + this[1, 1] * v.y + this[1, 2] * v.z,
            this[2, 0] * v.x + this[2, 1] * v.y + this[2, 2] * v.z
        )
    }
}
