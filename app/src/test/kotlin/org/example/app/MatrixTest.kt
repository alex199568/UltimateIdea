package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class MatrixTest {

    @Test
    fun `transposed should return correctly for non-square matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val expectedTransposed = Matrix(
            rows = 3,
            columns = 2,
            1, 4,
            2, 5,
            3, 6
        )

        assertEquals(expectedTransposed, matrix.transposed)
    }

    @Test
    fun `transposed should return correctly for square matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            1, 2,
            3, 4
        )
        val expectedTransposed = Matrix(
            rows = 2,
            columns = 2,
            1, 3,
            2, 4
        )

        assertEquals(expectedTransposed, matrix.transposed)
    }

    @Test
    fun `transposing twice should return the original matrix`() {
        val matrix = Matrix(
            rows = 3,
            columns = 2,
            1, 2,
            3, 4,
            5, 6
        )

        assertEquals(matrix, matrix.transposed.transposed)
    }

    @Test
    fun `transposed should work correctly for a single-row matrix`() {
        val matrix = Matrix(
            rows = 1,
            columns = 4,
            1, 2, 3, 4
        )
        val expectedTransposed = Matrix(
            rows = 4,
            columns = 1,
            1,
            2,
            3,
            4
        )

        assertEquals(expectedTransposed, matrix.transposed)
    }

    @Test
    fun `transposed should work correctly for a single-column matrix`() {
        val matrix = Matrix(
            rows = 4,
            columns = 1,
            1,
            2,
            3,
            4
        )
        val expectedTransposed = Matrix(
            rows = 1,
            columns = 4,
            1, 2, 3, 4
        )

        assertEquals(expectedTransposed, matrix.transposed)
    }

    @Test
    fun `constructor should initialize matrix correctly`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertEquals(2, matrix.rows)
        assertEquals(3, matrix.columns)
        assertEquals(1.0, matrix[0, 0])
        assertEquals(2.0, matrix[0, 1])
        assertEquals(3.0, matrix[0, 2])
        assertEquals(4.0, matrix[1, 0])
        assertEquals(5.0, matrix[1, 1])
        assertEquals(6.0, matrix[1, 2])
    }

    @Test
    fun `constructor should throw exception on invalid rows`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Matrix(
                rows = 0,
                columns = 3,
                1, 2, 3
            )
        }
        assertTrue(exception.message!!.contains("Number of rows must be positive"))
    }

    @Test
    fun `constructor should throw exception on invalid columns`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Matrix(
                rows = 2,
                columns = -3,
                1, 2, 3,
                4, 5, 6
            )
        }
        assertTrue(exception.message!!.contains("Number of columns must be positive"))
    }

    @Test
    fun `constructor should throw exception on mismatched elements size`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Matrix(
                rows = 2,
                columns = 3,
                1, 2, 3,
                4
            )
        }
        assertTrue(exception.message!!.contains("Number of elements (4) must match matrix size (2 × 3)"))
    }

    @Test
    fun `constructor with no items should initialize empty matrix correctly`() {
        val matrix = Matrix(rows = 2, columns = 3)

        assertEquals(2, matrix.rows)
        assertEquals(3, matrix.columns)
        assertEquals(0.0, matrix[0, 0])
        assertEquals(0.0, matrix[0, 1])
        assertEquals(0.0, matrix[0, 2])
        assertEquals(0.0, matrix[1, 0])
        assertEquals(0.0, matrix[1, 1])
        assertEquals(0.0, matrix[1, 2])
    }

    @Test
    fun `get operator should return correct element`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertEquals(1.0, matrix[0, 0])
        assertEquals(6.0, matrix[1, 2])
    }

    @Test
    fun `get operator should throw exception for invalid indices`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertFailsWith<IllegalArgumentException> { matrix[2, 2] }
        assertFailsWith<IllegalArgumentException> { matrix[-1, 0] }
    }

    @Test
    fun `set operator should update element correctly`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        matrix[0, 1] = 10.5
        matrix[1, 2] = -7.3

        assertEquals(10.5, matrix[0, 1])
        assertEquals(-7.3, matrix[1, 2])
    }

    @Test
    fun `set operator should throw exception for invalid indices`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertFailsWith<IllegalArgumentException> { matrix[2, 2] = 10 }
    }

    @Test
    fun `equals should return true for identical matrices`() {
        val matrix1 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val matrix2 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertTrue(matrix1 == matrix2)
    }

    @Test
    fun `equals should return false for different matrices`() {
        val matrix1 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val matrix2 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 7
        )

        assertTrue(matrix1 != matrix2)
    }

    @Test
    fun `hashCode should be consistent for equal matrices`() {
        val matrix1 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val matrix2 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        assertEquals(matrix1.hashCode(), matrix2.hashCode())
    }

    @Test
    fun `times should correctly multiply two compatible matrices`() {
        val matrix1 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val matrix2 = Matrix(
            rows = 3,
            columns = 2,
            7, 8,
            9, 10,
            11, 12
        )
        val result = matrix1 * matrix2

        assertEquals(2, result.rows)
        assertEquals(2, result.columns)
        assertEquals(58.0, result[0, 0])
        assertEquals(64.0, result[0, 1])
        assertEquals(139.0, result[1, 0])
        assertEquals(154.0, result[1, 1])
    }

    @Test
    fun `times should correctly multiply with identity matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            3, 5,
            7, 9
        )
        val identity = Matrix(
            rows = 2,
            columns = 2,
            1, 0,
            0, 1
        )
        val result = matrix * identity

        assertEquals(matrix, result)
    }

    @Test
    fun `times should throw exception for incompatible dimensions`() {
        val matrix1 = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )
        val matrix2 = Matrix(
            rows = 2,
            columns = 2,
            7, 8,
            9, 10
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            matrix1 * matrix2
        }
        assertTrue(exception.message!!.contains("Matrix dimensions are incompatible for multiplication"))
    }

    @Test
    fun `inverse should compute correctly for a square matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            4, 7,
            2, 6
        )
        val expectedInverse = Matrix(
            rows = 2,
            columns = 2,
            0.6, -0.7,
            -0.2, 0.4
        )

        assertEquals(expectedInverse, matrix.inverse)
    }

    @Test
    fun `product of matrix and its inverse should be identity`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            3, 3.5,
            3.2, 3.6
        )
        val identity = Matrix(
            rows = 2,
            columns = 2,
            1, 0,
            0, 1
        )
        val result = matrix * matrix.inverse

        for (i in 0 until result.rows) {
            for (j in 0 until result.columns) {
                assertEquals(identity[i, j], result[i, j], 1e-9)
            }
        }
    }

    @Test
    fun `inverse should throw exception for non-invertible matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
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
        val identity = Matrix(
            rows = 3,
            columns = 3,
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        )

        assertEquals(identity, identity.inverse)
    }

    @Test
    fun `inverse should throw exception for non-square matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 3,
            1, 2, 3,
            4, 5, 6
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            matrix.inverse
        }
        assertTrue(exception.message!!.contains("Matrix must be square to calculate inverse"))
    }

    @Test
    fun `submatrix should exclude the given row and column`() {
        val matrix = Matrix(
            rows = 3,
            columns = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )
        val expectedSubmatrix = Matrix(
            rows = 2,
            columns = 2,
            1, 3,
            7, 9
        )

        assertEquals(expectedSubmatrix, matrix.submatrix(1, 1))
    }

    @Test
    fun `submatrix should correctly handle a 2x2 matrix to produce 1x1 matrix`() {
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            1, 2,
            3, 4
        )
        val expectedSubmatrix = Matrix(
            rows = 1,
            columns = 1,
            1
        )

        assertEquals(expectedSubmatrix, matrix.submatrix(1, 1))
    }

    @Test
    fun `submatrix should throw exception for invalid indices`() {
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 2,
            columns = 2,
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
        val matrix = Matrix(
            rows = 3,
            columns = 3,
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
        val matrix = Matrix(
            rows = 2,
            columns = 2,
            4, 6,
            3, 8
        )

        assertEquals(14.0, matrix.det)
    }

    @Test
    fun `det should calculate correctly for 3x3 matrix`() {
        val matrix = Matrix(
            rows = 3,
            columns = 3,
            6, 1, 1,
            4, -2, 5,
            2, 8, 7
        )

        assertEquals(-306.0, matrix.det)
    }

    @Test
    fun `det should return 1 for identity matrix`() {
        val identity = Matrix(
            rows = 3,
            columns = 3,
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        )

        assertEquals(1.0, identity.det)
    }

    @Test
    fun `det should throw exception for non-square matrix`() {
        val matrix = Matrix(
            rows = 3,
            columns = 2,
            1, 2,
            3, 4,
            5, 6
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            matrix.det
        }
        assertTrue(exception.message!!.contains("Matrix must be square to calculate determinant"))
    }

    @Test
    fun `det should return zero for singular matrix`() {
        val matrix = Matrix(
            rows = 3,
            columns = 3,
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        )

        assertEquals(0.0, matrix.det)
    }
}