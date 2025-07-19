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

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Matrix($rows×$columns):\n")

        // Find max width for alignment
        var maxWidth = 0
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                maxWidth = maxOf(maxWidth, "%.2f".format(this[i, j]).length)
            }
        }

        // Build formatted string
        for (i in 0 until rows) {
            sb.append("  [")
            for (j in 0 until columns) {
                if (j > 0) sb.append(", ")
                sb.append("%.2f".format(this[i, j]).padStart(maxWidth))
            }
            sb.append("]\n")
        }
        return sb.toString()
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

    open operator fun times(other: Matrix): Matrix {
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