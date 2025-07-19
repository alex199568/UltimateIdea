package org.example.app

class Matrix(
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
    
    val det: Double
        get() {
            require(rows == columns) { "Matrix must be square to calculate determinant" }
            if (rows == 1) return this[0, 0]
            if (rows == 2) {
                return this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
            }
            var sum = 0.0
            // Calculate using first row cofactor expansion
            for (j in 0 until columns) {
                sum += this[0, j] * cofactor(0, j)
            }
            return sum
        }
    
    fun submatrix(iExclude: Int, jExclude: Int): Matrix {
        require(iExclude in 0 until rows) { "Row index $iExclude out of bounds [0, $rows)" }
        require(jExclude in 0 until columns) { "Column index $jExclude out of bounds [0, $columns)" }

        val result = Matrix(rows - 1, columns - 1)

        var newRow = 0
        for (i in 0 until rows) {
            if (i == iExclude) continue

            var newCol = 0
            for (j in 0 until columns) {
                if (j == jExclude) continue
                result[newRow, newCol] = this[i, j]
                newCol++
            }
            newRow++
        }

        return result
    }
    
    fun minor(i: Int, j: Int): Double {
        return submatrix(i, j).det
    }
    
    fun cofactor(i: Int, j: Int): Double {
        val sign = if ((i + j) % 2 == 0) 1 else -1
        return sign * minor(i, j)
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
    
    val inverse by lazy {
        require(rows == columns) { "Matrix must be square to calculate inverse" }
        val d = det
        require(d != 0.0) { "Matrix is not invertible (determinant is zero)" }

        // Create adjoint matrix using cofactors
        val adjoint = Matrix(rows, columns).apply {
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    this[i, j] = this@Matrix.cofactor(j, i)  // Note: j,i for transpose
                }
            }
        }

        // Divide adjoint by determinant
        Matrix(rows, columns).apply {
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    this[i, j] = adjoint[i, j] / d
                }
            }
        }
    }
}
