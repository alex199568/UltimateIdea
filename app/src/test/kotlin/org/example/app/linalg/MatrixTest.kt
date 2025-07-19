package org.example.app.linalg

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
}