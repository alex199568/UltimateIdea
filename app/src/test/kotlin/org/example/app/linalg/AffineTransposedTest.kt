package org.example.app.linalg

import org.example.app.Epsilon
import org.example.app.Ray
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

    @Test
    fun testAffineTransposedVectorTransformation() {
        val matrix = AffineTransposed(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            0, 0, 0
        )
        val vector = Vector(1.0, 2.0, 3.0)
        val result = matrix * vector

        // Expected calculation:
        // x = 1*1 + 0*2 + 0*3 = 1
        // y = 0*1 + 1*2 + 0*3 = 2
        // z = 0*1 + 0*2 + 1*3 = 3

        assertEquals(1.0, result.x, Epsilon)
        assertEquals(2.0, result.y, Epsilon)
        assertEquals(3.0, result.z, Epsilon)
    }

    @Test
    fun testAffineTransposedVectorTransformationWithScaling() {
        val matrix = AffineTransposed(
            2, 0, 0,
            0, 3, 0,
            0, 0, 4,
            0, 0, 0
        )
        val vector = Vector(1.0, 1.0, 1.0)
        val result = matrix * vector

        // Expected calculation:
        // x = 2*1 + 0*1 + 0*1 = 2
        // y = 0*1 + 3*1 + 0*1 = 3
        // z = 0*1 + 0*1 + 4*1 = 4

        assertEquals(2.0, result.x, Epsilon)
        assertEquals(3.0, result.y, Epsilon)
        assertEquals(4.0, result.z, Epsilon)
    }

    @Test
    fun testAffineTransposedRayTransformationWithTranslationAndScaling() {
        val matrix = AffineTransposed(
            2, 0, 0,
            0, 3, 0,
            0, 0, 4,
            1, 2, 3
        )
        val ray = Ray(
            origin = Point(1.0, 1.0, 1.0),
            direction = Vector(1.0, 0.0, 0.0)
        )
        val transformedRay = matrix * ray

        // Expected calculations for origin:
        // x = 2*1 + 0*1 + 0*1 = 2
        // y = 0*1 + 3*1 + 0*1 = 3
        // z = 0*1 + 0*1 + 4*1 = 4
        // w = 1*1 + 2*1 + 3*1 + 1 = 7
        // Resulting origin: (2/7, 3/7, 4/7)

        // Expected calculations for direction:
        // x = 2*1 + 0*0 + 0*0 = 2
        // y = 0*1 + 3*0 + 0*0 = 0
        // z = 0*1 + 0*0 + 4*0 = 0

        val expected = Ray(
            Point(2.0 / 7.0, 3.0 / 7.0, 4.0 / 7.0),
            Vector(2.0, 0.0, 0.0)
        )

        assertEquals(expected, transformedRay)
    }

    @Test
    fun testAffineTransposedRayTransformationWithRotation() {
        val rotationMatrix = AffineTransposed(
            0, -1, 0,
            1, 0, 0,
            0, 0, 1,
            0, 0, 0
        )
        val ray = Ray(
            origin = Point(1.0, 0.0, 0.0),
            direction = Vector(1.0, 0.0, 0.0)
        )
        val transformedRay = rotationMatrix * ray

        // Expected calculations for origin:
        // x = 0*1 + (-1)*0 + 0*0 = 0
        // y = 1*1 + 0*0 + 0*0 = 1
        // z = 0*1 + 0*0 + 1*0 = 0

        // Expected calculations for direction:
        // x = 0*1 + (-1)*0 + 0*0 = 0
        // y = 1*1 + 0*0 + 0*0 = 1
        // z = 0*1 + 0*0 + 1*0 = 0

        val expected = Ray(
            Point(0, 1, 0),
            Vector(0, 1, 0)
        )

        assertEquals(expected, transformedRay)
    }
}
