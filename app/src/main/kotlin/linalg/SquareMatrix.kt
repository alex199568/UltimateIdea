package org.example.app.linalg

open class SquareMatrix(
    size: Int,
    vararg elements: Number = emptyArray<Number>()
) : Matrix(size, size, *elements) {

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
