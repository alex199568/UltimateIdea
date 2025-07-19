package org.example.app.linalg

import org.example.app.linalg.Affine.Companion.rotateX
import org.example.app.linalg.Affine.Companion.rotateY
import org.example.app.linalg.Affine.Companion.rotateZ
import kotlin.test.Test
import kotlin.test.assertEquals

class AffineTest {

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
}
