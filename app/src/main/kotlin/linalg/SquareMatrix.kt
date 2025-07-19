package org.example.app.linalg

class SquareMatrix(
    size: Int,
    vararg elements: Number = emptyArray<Number>()
) : Matrix(size, size, *elements) {
}
