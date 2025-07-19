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

    operator fun times(other: Affine): Affine {
        // Affine * Affine multiplication
        // Treat both as 4x4 matrices with implicit [0,0,0,1] last row
        val result = Affine()

        // Multiply as if both were 4x4 matrices
        for (i in 0 until 3) {
            for (j in 0 until 4) {
                var sum = 0.0
                // Standard matrix multiplication for the 3x4 part
                for (k in 0 until 3) {
                    sum += this[i, k] * other[k, j]
                }
                // Add contribution from implicit [0,0,0,1] row of this matrix
                // and implicit [0,0,0,1] column of other matrix
                if (j == 3) {
                    sum += this[i, 3] * 1.0  // this[i, 3] * other[3, 3] where other[3,3] = 1
                }
                result[i, j] = sum
            }
        }

        return result
    }

    override fun times(other: Matrix): Matrix {
        return when (other) {
            is Affine -> {
                times(other)
            }

            else -> {
                // For other matrix types, delegate to parent implementation
                // But we need to handle dimension compatibility
                when {
                    other.rows == 4 && other.columns == 4 -> {
                        // Multiply 4x4 with implicit last row
                        val result = Matrix(4, 4)

                        // Fill first 3 rows
                        for (i in 0 until 3) {
                            for (j in 0 until 4) {
                                var sum = 0.0
                                for (k in 0 until 4) {
                                    val thisVal = if (k < 4) this[i, k] else 0.0
                                    val otherVal = other[k, j]
                                    sum += thisVal * otherVal
                                }
                                // Add implicit last row contribution
                                if (i < 3 && j < 4) {
                                    sum += if (j == 3) this[i, 3] else 0.0
                                }
                                result[i, j] = sum
                            }
                        }

                        // Fill last row as [0, 0, 0, 1]
                        result[3, 0] = 0.0
                        result[3, 1] = 0.0
                        result[3, 2] = 0.0
                        result[3, 3] = 1.0

                        result
                    }

                    other.rows == 3 -> {
                        // Multiply Affine (3x4 with implicit row) with 3xN matrix
                        // We need to pad the other matrix to 4xN with [0,0,0,1] row
                        val result = Matrix(3, other.columns)

                        for (i in 0 until 3) {
                            for (j in 0 until other.columns) {
                                var sum = 0.0
                                // Multiply the 3x3 part
                                for (k in 0 until 3) {
                                    sum += this[i, k] * other[k, j]
                                }
                                // Add translation component only if we're dealing with homogeneous coordinates
                                // For a 3x3 matrix, we assume no translation component should be added
                                result[i, j] = sum
                            }
                        }

                        result
                    }


                    else -> {
                        throw IllegalArgumentException(
                            "Cannot multiply Affine (3×4) with ${other.rows}×${other.columns} matrix"
                        )
                    }
                }
            }
        }
    }

    val inverse by lazy {
        // Extract the 3x3 rotation/scale matrix (upper-left)
        val r = SquareMatrix(
            3,
            this[0, 0], this[0, 1], this[0, 2],
            this[1, 0], this[1, 1], this[1, 2],
            this[2, 0], this[2, 1], this[2, 2]
        )

        // Calculate the inverse of the rotation/scale matrix
        val rInv = r.inverse

        // Extract the translation vector (rightmost column)
        val tx = this[0, 3]
        val ty = this[1, 3]
        val tz = this[2, 3]

        // Calculate -R⁻¹t (negative of inverse rotation times translation)
        val newTx = -(rInv[0, 0] * tx + rInv[0, 1] * ty + rInv[0, 2] * tz)
        val newTy = -(rInv[1, 0] * tx + rInv[1, 1] * ty + rInv[1, 2] * tz)
        val newTz = -(rInv[2, 0] * tx + rInv[2, 1] * ty + rInv[2, 2] * tz)

        // Construct the inverse affine matrix
        Affine(
            rInv[0, 0], rInv[0, 1], rInv[0, 2], newTx,
            rInv[1, 0], rInv[1, 1], rInv[1, 2], newTy,
            rInv[2, 0], rInv[2, 1], rInv[2, 2], newTz
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