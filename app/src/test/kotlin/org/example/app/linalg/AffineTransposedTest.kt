package org.example.app.linalg

import org.example.app.Epsilon
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class AffineTransposedTest {

    @Test
    fun testAffineTransposedPointTransformationWithTranslation() {
        val matrix = AffineTransposed(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, 2, 3
        )
        val point = Point(1.0, 2.0, 3.0)
        val result = matrix * point

        // Expected calculation:
        // x = 1*1 + 0*2 + 0*3 = 1
        // y = 0*1 + 1*2 + 0*3 = 2
        // z = 0*1 + 0*2 + 1*3 = 3
        // w = 1*1 + 2*2 + 3*3 + 1 = 1 + 4 + 9 + 1 = 15
        // Result: (1/15, 2/15, 3/15)

        assertEquals(1.0/15.0, result.x, Epsilon)
        assertEquals(2.0/15.0, result.y, Epsilon)
        assertEquals(3.0/15.0, result.z, Epsilon)
    }

    @Test
    fun testAffineTransposedPointTransformationWithPerspective() {
        val matrix = AffineTransposed(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            0.5, 0.5, 1.0
        )
        val point = Point(2.0, 2.0, 2.0)
        val result = matrix * point

        // Expected calculation:
        // x = 1*2 + 0*2 + 0*2 = 2
        // y = 0*2 + 1*2 + 0*2 = 2
        // z = 0*2 + 0*2 + 1*2 = 2
        // w = 0.5*2 + 0.5*2 + 1.0*2 + 1 = 1 + 1 + 2 + 1 = 5
        // Result: (2/5, 2/5, 2/5) = (0.4, 0.4, 0.4)

        assertEquals(0.4, result.x, Epsilon)
        assertEquals(0.4, result.y, Epsilon)
        assertEquals(0.4, result.z, Epsilon)
    }

    @Test
    fun testAffineTransposedPointTransformationWithSkewedLastRow() {
        val matrix = AffineTransposed(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, -1, 2
        )
        val point = Point(1.0, 1.0, 1.0)
        val result = matrix * point

        // Expected calculation:
        // x = 1*1 + 0*1 + 0*1 = 1
        // y = 0*1 + 1*1 + 0*1 = 1
        // z = 0*1 + 0*1 + 1*1 = 1
        // w = 1*1 + (-1)*1 + 2*1 + 1 = 1 - 1 + 2 + 1 = 3
        // Result: (1/3, 1/3, 1/3)

        assertEquals(1.0/3.0, result.x, Epsilon)
        assertEquals(1.0/3.0, result.y, Epsilon)
        assertEquals(1.0/3.0, result.z, Epsilon)
    }

}
