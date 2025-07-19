package org.example.app.linalg

import org.example.app.eq

open class Matrix(
    val rows: Int,
    val columns: Int,
    vararg elements: Number = emptyArray<Number>()
) {

    init {
        require(rows > 0) { "Number of rows must be positive, got $rows" }
        require(columns > 0) { "Number of columns must be positive, got $columns" }

        if (elements.isNotEmpty()) {
            require(elements.size == rows * columns) {
                "Number of elements (${elements.size}) must match matrix size ($rows × $columns)"
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        val m = other as? Matrix ?: return false
        if (rows != m.rows || columns != m.columns) return false
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                if (!(this[i, j] eq m[i, j])) return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = rows
        result = 31 * result + columns
        result = 31 * result + items.contentHashCode()
        return result
    }

    private val items = Array(rows * columns) { elements.getOrNull(it)?.toDouble() ?: 0.0 }

    /**
     * Calculates the index into the internal array representation based on matrix coordinates.
     *
     * @param i Row index must be in range [0, rows)
     * @param j Column index must be in range [0, columns)
     * @return The linear array index corresponding to position (i,j) in the matrix
     * @throws IllegalArgumentException if indices are out of bounds
     */
    fun index(i: Int, j: Int): Int {
        require(i in 0 until rows) { "Row index $i out of bounds [0, $rows)" }
        require(j in 0 until columns) { "Column index $j out of bounds [0, $columns)" }
        return j + i * columns
    }

    operator fun get(i: Int, j: Int): Double {
        return items[index(i, j)]
    }

    operator fun set(i: Int, j: Int, n: Number) {
        items[index(i, j)] = n.toDouble()
    }

    operator fun times(other: Matrix): Matrix {
        require(columns == other.rows) {
            "Matrix dimensions are incompatible for multiplication: " +
                    "$rows×$columns and ${other.rows}×${other.columns}"
        }

        val result = Matrix(rows, other.columns)

        for (i in 0 until rows) {
            for (j in 0 until other.columns) {
                var sum = 0.0
                for (k in 0 until columns) {
                    sum += this[i, k] * other[k, j]
                }
                result[i, j] = sum
            }
        }

        return result
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

    operator fun times(p: Point): Point {
        require(columns == 4 && rows == 4) {
            "Matrix must be 4x4 for point multiplication, got ${rows}x${columns}"
        }

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

    val transposed by lazy {
        val result = Matrix(columns, rows)
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                result[j, i] = this[i, j]
            }
        }
        result
    }
}