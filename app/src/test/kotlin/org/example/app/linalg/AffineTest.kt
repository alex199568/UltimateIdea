package org.example.app.linalg

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
}
