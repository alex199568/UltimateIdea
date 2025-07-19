package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class MatrixTest {

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
}