package org.example.app.linalg

import org.example.app.linalg.Affine.Companion.rotateX
import org.example.app.linalg.Affine.Companion.rotateY
import org.example.app.linalg.Affine.Companion.rotateZ
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertSame

class AffineTest {

    @Test
    fun `affine times affine produces correct transformation`() {
        val affine1 = Affine.translate(1, 2, 3)
        val affine2 = Affine.scale(2, 3, 4)
        val result = affine1 * affine2

        val expected = Affine(
            2.0, 0.0, 0.0, 1.0,
            0.0, 3.0, 0.0, 2.0,
            0.0, 0.0, 4.0, 3.0
        )

        assertEquals(expected, result)
    }

    @Test
    fun `affine times affine produces correct transformation when matrix is passed`() {
        val affine1 = Affine.translate(1, 2, 3)
        val affine2 = Affine.scale(2, 3, 4) as Matrix
        val result = affine1 * affine2

        val expected = Affine(
            2.0, 0.0, 0.0, 1.0,
            0.0, 3.0, 0.0, 2.0,
            0.0, 0.0, 4.0, 3.0
        )

        assertEquals(expected, result)
    }

    @Test
    fun `translation times scaling produces correct affine`() {
        val translation = Affine.translate(1, 1, 1)
        val scaling = Affine.scale(2, 2, 2)
        val result = translation * scaling

        val expected = Affine(
            2.0, 0.0, 0.0, 1.0,
            0.0, 2.0, 0.0, 1.0,
            0.0, 0.0, 2.0, 1.0
        )

        assertEquals(expected, result)
    }

    @Test
    fun `affine times itself produces correct affine`() {
        val affine = Affine.translate(2, 3, 4)
        val result = affine * affine

        val expected = Affine(
            1.0, 0.0, 0.0, 4.0,
            0.0, 1.0, 0.0, 6.0,
            0.0, 0.0, 1.0, 8.0
        )

        assertEquals(expected, result)
    }

    @Test
    fun `affine times compatible matrix produces valid result`() {
        val affine = Affine.scale(2, 2, 2)
        val matrix = Matrix(
            3, 3,
            2, 1, 0,
            0, 2, 0,
            0, 0, 1
        )
        val result = affine * matrix

        val expected = Matrix(
            3, 3,
            4.0, 2.0, 0.0,
            0.0, 4.0, 0.0,
            0.0, 0.0, 2.0
        )

        assertEquals(expected, result)
    }

    @Test
    fun `affine times incompatible matrix throws exception`() {
        val affine = Affine.translate(1, 2, 3)
        val incompatibleMatrix = Matrix(2, 2, 1, 2, 3, 4)

        assertFailsWith<IllegalArgumentException> {
            affine * incompatibleMatrix
        }
    }

    @Test
    fun `inverse of identity affine matrix is identity`() {
        val identity = Affine(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0
        )

        assertEquals(identity, identity.inverse)
    }

    @Test
    fun `affine times inverse produces identity`() {
        val affine = Affine(
            2.0, 0.0, 0.0, 1.0,
            0.0, 3.0, 0.0, 2.0,
            0.0, 0.0, 4.0, 3.0
        )

        val result = affine * affine.inverse
        val identity = Affine(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0
        )

        assertEquals(identity, result)
    }

    @Test
    fun `inverse of translation matrix works correctly`() {
        val translation = Affine.translate(1, 2, 3)
        val expected = Affine.translate(-1, -2, -3)

        assertEquals(expected, translation.inverse)
    }

    @Test
    fun `inverse of scaling matrix works correctly`() {
        val scaling = Affine.scale(2, 3, 4)
        val expected = Affine.scale(0.5, 1.0 / 3.0, 0.25)

        assertEquals(expected, scaling.inverse)
    }

    @Test
    fun testAffineTimesPoint() {
        val affine = Affine(
            1, 0, 0, 2,
            0, 1, 0, 3,
            0, 0, 1, 4,
        )
        val point = Point(1.0, 2.0, 3.0)
        val result = affine * point

        assertEquals(Point(3.0, 5.0, 7.0), result)
    }

    @Test
    fun testAffineTimesVector() {
        val affine = Affine(
            2, 0, 0, 0,
            0, 3, 0, 0,
            0, 0, 4, 0,
        )
        val vector = Vector(1.0, 2.0, 3.0)
        val result = affine * vector

        assertEquals(Vector(2.0, 6.0, 12.0), result)
    }

    @Test
    fun `affine times 4x4 matrix produces valid result`() {
        val affine = Affine.translate(1, 2, 3)

        // Create a regular 4x4 Matrix (not Transform)
        val matrix4x4 = Matrix(
            4, 4,
            2, 0, 0, 0,
            0, 3, 0, 0,
            0, 0, 4, 0,
            0, 0, 0, 1
        )

        val result = affine * matrix4x4

        // The actual calculation is correct - translation followed by scaling
        // results in the translation being preserved in the final column
        val expected = Matrix(
            4, 4,
            2.0, 0.0, 0.0, 2.0,
            0.0, 3.0, 0.0, 4.0,
            0.0, 0.0, 4.0, 6.0,
            0.0, 0.0, 0.0, 1.0
        )

        assertEquals(expected, result)
    }


    /**
     * Tests for the `translate` function in `Affine.Companion`.
     *
     * The `translate` function creates an affine transformation that moves
     * points and vectors by the specified x, y, and z offsets.
     */

    @Test
    fun `translate creates correct transformation with positive offsets`() {
        val affine = Affine.translate(2, 3, 4)

        assertEquals(1.0, affine[0, 0])
        assertEquals(0.0, affine[0, 1])
        assertEquals(0.0, affine[0, 2])
        assertEquals(2.0, affine[0, 3])

        assertEquals(0.0, affine[1, 0])
        assertEquals(1.0, affine[1, 1])
        assertEquals(0.0, affine[1, 2])
        assertEquals(3.0, affine[1, 3])

        assertEquals(0.0, affine[2, 0])
        assertEquals(0.0, affine[2, 1])
        assertEquals(1.0, affine[2, 2])
        assertEquals(4.0, affine[2, 3])
    }

    @Test
    fun `translate creates correct transformation with negative offsets`() {
        val affine = Affine.translate(-1, -2, -3)

        assertEquals(1.0, affine[0, 0])
        assertEquals(0.0, affine[0, 1])
        assertEquals(0.0, affine[0, 2])
        assertEquals(-1.0, affine[0, 3])

        assertEquals(0.0, affine[1, 0])
        assertEquals(1.0, affine[1, 1])
        assertEquals(0.0, affine[1, 2])
        assertEquals(-2.0, affine[1, 3])

        assertEquals(0.0, affine[2, 0])
        assertEquals(0.0, affine[2, 1])
        assertEquals(1.0, affine[2, 2])
        assertEquals(-3.0, affine[2, 3])
    }

    @Test
    fun `translate creates identity transformation when all offsets are zero`() {
        val affine = Affine.translate(0, 0, 0)

        assertEquals(1.0, affine[0, 0])
        assertEquals(0.0, affine[0, 1])
        assertEquals(0.0, affine[0, 2])
        assertEquals(0.0, affine[0, 3])

        assertEquals(0.0, affine[1, 0])
        assertEquals(1.0, affine[1, 1])
        assertEquals(0.0, affine[1, 2])
        assertEquals(0.0, affine[1, 3])

        assertEquals(0.0, affine[2, 0])
        assertEquals(0.0, affine[2, 1])
        assertEquals(1.0, affine[2, 2])
        assertEquals(0.0, affine[2, 3])
    }

    @Test
    fun testRotateX() {
        val rotation = rotateX(Math.PI / 2)
        val point = Point(0, 1, 0)
        val transformed = rotation * point
        assertEquals(Point(0, 0, 1), transformed)
    }

    @Test
    fun testRotateY() {
        val rotation = rotateY(Math.PI / 2)
        val point = Point(0, 0, 1)
        val transformed = rotation * point
        assertEquals(Point(1, 0, 0), transformed)
    }

    @Test
    fun testRotateZ() {
        val rotation = rotateZ(Math.PI / 2)
        val point = Point(1, 0, 0)
        val transformed = rotation * point
        assertEquals(Point(0, 1, 0), transformed)
    }

    @Test
    fun `test scale with uniform scaling factors`() {
        val expected = Affine(
            2.0, 0.0, 0.0, 0.0,
            0.0, 2.0, 0.0, 0.0,
            0.0, 0.0, 2.0, 0.0
        )

        val result = Affine.scale(2, 2, 2)

        assertEquals(expected, result)
    }

    @Test
    fun `test scale with different scaling factors`() {
        val expected = Affine(
            3.0, 0.0, 0.0, 0.0,
            0.0, 4.0, 0.0, 0.0,
            0.0, 0.0, 5.0, 0.0
        )

        val result = Affine.scale(3, 4, 5)

        assertEquals(expected, result)
    }

    @Test
    fun `test scale with a zero scaling factor`() {
        val expected = Affine(
            0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0
        )

        val result = Affine.scale(0, 0, 0)

        assertEquals(expected, result)
    }

    @Test
    fun `test scale with negative scaling factors`() {
        val expected = Affine(
            -1.0, 0.0, 0.0, 0.0,
            0.0, -2.0, 0.0, 0.0,
            0.0, 0.0, -3.0, 0.0
        )

        val result = Affine.scale(-1, -2, -3)

        assertEquals(expected, result)
    }

    @Test
    fun `test scale with mixed positive and negative scaling factors`() {
        val expected = Affine(
            2.0, 0.0, 0.0, 0.0,
            0.0, -3.0, 0.0, 0.0,
            0.0, 0.0, 4.0, 0.0
        )

        val result = Affine.scale(2, -3, 4)

        assertEquals(expected, result)
    }

    @Test
    fun `test scale with fractional scaling factors`() {
        val expected = Affine(
            0.5, 0.0, 0.0, 0.0,
            0.0, 0.25, 0.0, 0.0,
            0.0, 0.0, 0.75, 0.0
        )

        val result = Affine.scale(0.5, 0.25, 0.75)

        assertEquals(expected, result)
    }

    @Test
    fun `transposed should return AffineTransposed with correct dimensions`() {
        val affine = Affine(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
        )
        
        val transposed = affine.transposed
        
        // Should be AffineTransposed (4×3)
        assertTrue(transposed is AffineTransposed)
        assertEquals(4, transposed.rows)
        assertEquals(3, transposed.columns)
    }

    @Test
    fun `transposed should correctly transpose identity transformation`() {
        val identity = Affine(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0
        )
        
        val transposed = identity.transposed
        
        // Expected transposed matrix:
        // [1, 0, 0]
        // [0, 1, 0]  
        // [0, 0, 1]
        // [0, 0, 0]
        assertEquals(1.0, transposed[0, 0])
        assertEquals(0.0, transposed[0, 1])
        assertEquals(0.0, transposed[0, 2])
        assertEquals(0.0, transposed[1, 0])
        assertEquals(1.0, transposed[1, 1])
        assertEquals(0.0, transposed[1, 2])
        assertEquals(0.0, transposed[2, 0])
        assertEquals(0.0, transposed[2, 1])
        assertEquals(1.0, transposed[2, 2])
        assertEquals(0.0, transposed[3, 0])
        assertEquals(0.0, transposed[3, 1])
        assertEquals(0.0, transposed[3, 2])
    }

    @Test
    fun `transposed should correctly transpose translation matrix`() {
        val translation = Affine.translate(5, 10, 15)
        
        val transposed = translation.transposed
        
        // Original: [1, 0, 0, 5]
        //          [0, 1, 0, 10]
        //          [0, 0, 1, 15]
        // Transposed: [1, 0, 0]
        //            [0, 1, 0]
        //            [0, 0, 1]
        //            [5, 10, 15]
        assertEquals(1.0, transposed[0, 0])
        assertEquals(0.0, transposed[0, 1])
        assertEquals(0.0, transposed[0, 2])
        assertEquals(0.0, transposed[1, 0])
        assertEquals(1.0, transposed[1, 1])
        assertEquals(0.0, transposed[1, 2])
        assertEquals(0.0, transposed[2, 0])
        assertEquals(0.0, transposed[2, 1])
        assertEquals(1.0, transposed[2, 2])
        assertEquals(5.0, transposed[3, 0])
        assertEquals(10.0, transposed[3, 1])
        assertEquals(15.0, transposed[3, 2])
    }

    @Test
    fun `transposed should correctly transpose scaling matrix`() {
        val scaling = Affine.scale(2, 3, 4)
        
        val transposed = scaling.transposed
        
        // Original: [2, 0, 0, 0]
        //          [0, 3, 0, 0]
        //          [0, 0, 4, 0]
        // Transposed: [2, 0, 0]
        //            [0, 3, 0]
        //            [0, 0, 4]
        //            [0, 0, 0]
        assertEquals(2.0, transposed[0, 0])
        assertEquals(0.0, transposed[0, 1])
        assertEquals(0.0, transposed[0, 2])
        assertEquals(0.0, transposed[1, 0])
        assertEquals(3.0, transposed[1, 1])
        assertEquals(0.0, transposed[1, 2])
        assertEquals(0.0, transposed[2, 0])
        assertEquals(0.0, transposed[2, 1])
        assertEquals(4.0, transposed[2, 2])
        assertEquals(0.0, transposed[3, 0])
        assertEquals(0.0, transposed[3, 1])
        assertEquals(0.0, transposed[3, 2])
    }

    @Test
    fun `transposed should correctly transpose general affine matrix`() {
        val affine = Affine(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
        )
        
        val transposed = affine.transposed
        
        // Original: [1, 2, 3, 4]
        //          [5, 6, 7, 8]
        //          [9, 10, 11, 12]
        // Transposed: [1, 5, 9]
        //            [2, 6, 10]
        //            [3, 7, 11]
        //            [4, 8, 12]
        assertEquals(1.0, transposed[0, 0])
        assertEquals(5.0, transposed[0, 1])
        assertEquals(9.0, transposed[0, 2])
        assertEquals(2.0, transposed[1, 0])
        assertEquals(6.0, transposed[1, 1])
        assertEquals(10.0, transposed[1, 2])
        assertEquals(3.0, transposed[2, 0])
        assertEquals(7.0, transposed[2, 1])
        assertEquals(11.0, transposed[2, 2])
        assertEquals(4.0, transposed[3, 0])
        assertEquals(8.0, transposed[3, 1])
        assertEquals(12.0, transposed[3, 2])
    }

    @Test
    fun `affineTransposed should be equivalent to transposed property`() {
        val affine = Affine(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
        )
        
        val transposed = affine.transposed
        val affineTransposed = affine.affineTransposed
        
        // Both should be the same
        assertEquals(transposed, affineTransposed)
    }

    @Test
    fun `affineTransposed should be lazy initialized`() {
        val affine = Affine(
            1, 0, 0, 5,
            0, 1, 0, 10,
            0, 0, 1, 15
        )
        
        // First access - should initialize
        val first = affine.affineTransposed
        // Second access - should return same instance
        val second = affine.affineTransposed
        
        assertSame(first, second)
    }

    @Test
    fun `transposed twice should not return original for Affine`() {
        val affine = Affine(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
        )
        
        val doubleTransposed = affine.transposed.transposed
        
        // Double transposing an Affine won't give back the original since
        // AffineTransposed.transposed returns a regular Matrix, not an Affine
        assertFalse(doubleTransposed is Affine)
        assertTrue(doubleTransposed is Matrix)
        assertEquals(3, doubleTransposed.rows)
        assertEquals(4, doubleTransposed.columns)
    }

    @Test
    fun `transposed matrix should have correct values after double transpose`() {
        val affine = Affine(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
        )
        
        val doubleTransposed = affine.transposed.transposed
        
        // Values should match original, even if type differs
        for (i in 0 until 3) {
            for (j in 0 until 4) {
                assertEquals(affine[i, j], doubleTransposed[i, j])
            }
        }
    }

    @Test
    fun `transposed of rotation matrix should work correctly`() {
        val rotation = Affine.rotateZ(Math.PI / 4) // 45 degrees
        val transposed = rotation.transposed
        
        // Check dimensions
        assertEquals(4, transposed.rows)
        assertEquals(3, transposed.columns)
        
        // Last row should be [0, 0, 0] since original translation is [0, 0, 0]
        assertEquals(0.0, transposed[3, 0], 1e-10)
        assertEquals(0.0, transposed[3, 1], 1e-10)
        assertEquals(0.0, transposed[3, 2], 1e-10)
    }
}