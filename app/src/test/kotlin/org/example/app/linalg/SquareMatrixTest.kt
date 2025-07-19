package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals

class SquareMatrixTest {

    @Test
    fun squareMatrix() {
        val matrix = SquareMatrix(
            2,
            1.0, 2.0,
            3.0, 4.0
        )

        assertEquals(1.0, matrix[0, 0])
        assertEquals(2.0, matrix[0, 1])
        assertEquals(3.0, matrix[1, 0])
        assertEquals(4.0, matrix[1, 1])
    }
}
