package org.example.app

class Matrix(
    val rows: Int,
    val columns: Int,
    vararg elements: Number
) {

    init {
        require(rows > 0) { "Number of rows must be positive, got $rows" }
        require(columns > 0) { "Number of columns must be positive, got $columns" }
        require(elements.size == rows * columns) {
            "Number of elements (${elements.size}) must match matrix size ($rows × $columns)"
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

    private val items = Array(rows * columns) { elements[it].toDouble() }

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
}
