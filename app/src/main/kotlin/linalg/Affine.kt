package org.example.app.linalg

import kotlin.math.cos
import kotlin.math.sin

class Affine(
    vararg elements: Number = emptyArray<Number>()
) : Matrix(3, 4, *elements) {

    operator fun times(p: Point): Point {
        val x = this[0, 0] * p.x + this[0, 1] * p.y + this[0, 2] * p.z + this[0, 3]
        val y = this[1, 0] * p.x + this[1, 1] * p.y + this[1, 2] * p.z + this[1, 3]
        val z = this[2, 0] * p.x + this[2, 1] * p.y + this[2, 2] * p.z + this[2, 3]

        return Point(x, y, z)
    }

    operator fun times(v: Vector): Vector {
        return Vector(
            this[0, 0] * v.x + this[0, 1] * v.y + this[0, 2] * v.z,
            this[1, 0] * v.x + this[1, 1] * v.y + this[1, 2] * v.z,
            this[2, 0] * v.x + this[2, 1] * v.y + this[2, 2] * v.z
        )
    }
    
    companion object {
        
        fun translate(x: Number, y: Number, z: Number): Affine {
            return Affine(
                1, 0, 0, x,
                0, 1, 0, y,
                0, 0, 1, z
            )
        }

        fun rotateX(rads: Number): Affine {
            val r = rads.toDouble()
            val cos = cos(r)
            val sin = sin(r)
            return Affine(
                1, 0, 0, 0,
                0, cos, -sin, 0,
                0, sin, cos, 0
            )
        }

        fun rotateY(rads: Number): Affine {
            val r = rads.toDouble()
            val cos = cos(r)
            val sin = sin(r)
            return Affine(
                cos, 0, sin, 0,
                0, 1, 0, 0,
                -sin, 0, cos, 0
            )
        }

        fun rotateZ(rads: Number): Affine {
            val r = rads.toDouble()
            val cos = cos(r)
            val sin = sin(r)
            return Affine(
                cos, -sin, 0, 0,
                sin, cos, 0, 0,
                0, 0, 1, 0
            )
        }
        
        fun scale(x: Number, y: Number, z: Number): Affine {
            return Affine(
                x, 0, 0, 0,
                0, y, 0, 0,
                0, 0, z, 0
            )
        }
    }
}
