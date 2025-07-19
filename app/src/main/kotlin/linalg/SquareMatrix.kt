package org.example.app.linalg

open class SquareMatrix(
    size: Int,
    vararg elements: Number = emptyArray<Number>()
) : Matrix(size, size, *elements) {

    val det by lazy {
        if (rows == 1) return@lazy this[0, 0]
        if (rows == 2) {
            return@lazy this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
        }
        var sum = 0.0
        // Calculate using first row cofactor expansion
        for (j in 0 until columns) {
            sum += this[0, j] * cofactor(0, j)
        }
        sum
    }

    fun minor(i: Int, j: Int): Double {
        return submatrix(i, j).det
    }

    fun cofactor(i: Int, j: Int): Double {
        val sign = if ((i + j) % 2 == 0) 1 else -1
        return sign * minor(i, j)
    }

    fun submatrix(iExclude: Int, jExclude: Int): SquareMatrix {
        require(iExclude in 0 until rows) { "Row index $iExclude out of bounds [0, $rows)" }
        require(jExclude in 0 until columns) { "Column index $jExclude out of bounds [0, $columns)" }

        val result = SquareMatrix(rows - 1)

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

    val inverse by lazy {
        val d = det
        require(d != 0.0) { "Matrix is not invertible (determinant is zero)" }

        Matrix(rows, columns).apply {
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    // Note: j,i for transpose
                    this[i, j] = this@SquareMatrix.cofactor(j, i) / d
                }
            }
        }
    }
}
