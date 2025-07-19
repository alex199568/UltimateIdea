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

    @Test
    fun `submatrix should exclude the given row and column`() {
        val matrix = SquareMatrix(
            size = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )
        val expectedSubmatrix = SquareMatrix(
            size = 2,
            1, 3,
            7, 9
        )

        assertEquals(expectedSubmatrix, matrix.submatrix(1, 1))
    }

    @Test
    fun `submatrix should correctly handle a 2x2 matrix to produce 1x1 matrix`() {
        val matrix = SquareMatrix(
            size = 2,
            1, 2,
            3, 4
        )
        val expectedSubmatrix = SquareMatrix(
            size = 1,
            1
        )

        assertEquals(expectedSubmatrix, matrix.submatrix(1, 1))
    }

    @Test
    fun `submatrix should throw exception for invalid indices`() {
        val matrix = SquareMatrix(
            size = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )

        assertFailsWith<IllegalArgumentException> { matrix.submatrix(-1, 1) }
        assertFailsWith<IllegalArgumentException> { matrix.submatrix(3, 1) }
        assertFailsWith<IllegalArgumentException> { matrix.submatrix(1, 3) }
    }

    @Test
    fun `cofactor should compute correctly for 3x3 matrix`() {
        val matrix = SquareMatrix(
            size = 3,
            2, -1, 0,
            3, 4, 5,
            1, -2, -1
        )
        // Cofactor (0, 0) = minor (0, 0) = det(submatrix(0, 0)) = det([4, 5;-2, -1])
        assertEquals(6.0, matrix.cofactor(0, 0))
        // Cofactor (0, 1) = -minor(0, 1) = -det(submatrix(0, 1)) = -det([3, 5;1, -1])
        assertEquals(8.0, matrix.cofactor(0, 1))
        // Cofactor (0, 2) = minor (0, 2) = det(submatrix(0, 2)) = det([3, 4;1, -2])
        assertEquals(-10.0, matrix.cofactor(0, 2))
    }

    @Test
    fun `cofactor should alternate signs in a 3x3 matrix`() {
        val matrix = SquareMatrix(
            size = 3,
            2, -1, 0,
            3, 4, 5,
            1, -2, -1
        )

        assertTrue(matrix.cofactor(0, 0) > 0)  // Sign (+)
        assertTrue(matrix.cofactor(0, 1) > 0)  // Sign (-)
        assertTrue(matrix.cofactor(0, 2) < 0)  // Sign (+)
    }

    @Test
    fun `cofactor should throw exception for invalid indices`() {
        val matrix = SquareMatrix(
            size = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )

        assertFailsWith<IllegalArgumentException> { matrix.cofactor(-1, 0) }
        assertFailsWith<IllegalArgumentException> { matrix.cofactor(3, 0) }
        assertFailsWith<IllegalArgumentException> { matrix.cofactor(0, 3) }
    }

    @Test
    fun `minor should compute correctly for a 3x3 matrix`() {
        val matrix = SquareMatrix(
            size = 3,
            3, 5, 7,
            2, 1, 4,
            6, 8, 9
        )
        // Minor(0, 0) = det([[1, 4], [8, 9]]) = (1 * 9) - (4 * 8) = 9 - 32 = -23
        assertEquals(-23.0, matrix.minor(0, 0))
        // Minor(1, 1) = det([[3, 7], [6, 9]]) = (3 * 9) - (7 * 6) = 27 - 42 = -15
        assertEquals(-15.0, matrix.minor(1, 1))
        // Minor(2, 2) = det([[3, 5], [2, 1]]) = (3 * 1) - (5 * 2) = 3 - 10 = -7
        assertEquals(-7.0, matrix.minor(2, 2))
    }

    @Test
    fun `minor should compute correctly for a 2x2 matrix`() {
        val matrix = SquareMatrix(
            size = 2,
            4, 6,
            3, 8
        )
        // Minor(0, 0) = det([[8]]) = 8
        assertEquals(8.0, matrix.minor(0, 0))
        // Minor(0, 1) = det([[3]]) = 3
        assertEquals(3.0, matrix.minor(0, 1))
    }

    @Test
    fun `minor should throw exception for invalid indices`() {
        val matrix = SquareMatrix(
            size = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )

        assertFailsWith<IllegalArgumentException> { matrix.minor(-1, 0) }
        assertFailsWith<IllegalArgumentException> { matrix.minor(3, 0) }
        assertFailsWith<IllegalArgumentException> { matrix.minor(0, 3) }
    }

    @Test
    fun `det should calculate correctly for 2x2 matrix`() {
        val matrix = SquareMatrix(
            size = 2,
            4, 6,
            3, 8
        )

        assertEquals(14.0, matrix.det)
    }

    @Test
    fun `det should calculate correctly for 3x3 matrix`() {
        val matrix = SquareMatrix(
            size = 3,
            6, 1, 1,
            4, -2, 5,
            2, 8, 7
        )

        assertEquals(-306.0, matrix.det)
    }

    @Test
    fun `det should return 1 for identity matrix`() {
        val identity = SquareMatrix(
            size = 3,
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        )

        assertEquals(1.0, identity.det)
    }

    @Test
    fun `det should return zero for singular matrix`() {
        val matrix = SquareMatrix(
            size = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )

        assertEquals(0.0, matrix.det)
    }
}
