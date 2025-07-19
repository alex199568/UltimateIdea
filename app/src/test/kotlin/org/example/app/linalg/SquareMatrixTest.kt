package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

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

    @Test
    fun `inverse should compute correctly for a square matrix`() {
        val matrix = SquareMatrix(
            size = 2,
            4, 7,
            2, 6
        )
        val expectedInverse = SquareMatrix(
            size = 2,
            0.6, -0.7,
            -0.2, 0.4
        )

        assertEquals(expectedInverse, matrix.inverse)
    }

    @Test
    fun `product of matrix and its inverse should be identity`() {
        val matrix = SquareMatrix(
            size = 2,
            3, 3.5,
            3.2, 3.6
        )
        val identity = SquareMatrix(
            size = 2,
            1, 0,
            0, 1
        )
        val result = matrix * matrix.inverse

        assertEquals(identity, result)
    }

    @Test
    fun `inverse should throw exception for non-invertible matrix`() {
        val matrix = SquareMatrix(
            size = 2,
            1, 2,
            2, 4
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            matrix.inverse
        }
        assertTrue(exception.message!!.contains("Matrix is not invertible"))
    }

    @Test
    fun `inverse of identity matrix should be itself`() {
        val identity = SquareMatrix(
            size = 3,
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        )

        assertEquals(identity, identity.inverse)
    }
}
