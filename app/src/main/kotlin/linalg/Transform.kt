package org.example.app.linalg

class Transform(
    vararg elements: Number = emptyArray<Number>()
) : SquareMatrix(4, *elements) {
}
