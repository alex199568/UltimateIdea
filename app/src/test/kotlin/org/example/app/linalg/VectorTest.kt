package org.example.app.linalg

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.math.sqrt
import kotlin.test.assertEquals

class VectorTest {

    @Test
    fun `constructor with Number values`() {
        val vector = Vector(1, 2.5, 3L)
        Assertions.assertEquals(1.0, vector.x)
        Assertions.assertEquals(2.5, vector.y)
        Assertions.assertEquals(3.0, vector.z)
    }

    @Test
    fun `plus operator should add vectors properly`() {
        val v1 = Vector(1, 2, 3)
        val v2 = Vector(4, 5, 6)
        val result = v1 + v2
        Assertions.assertEquals(Vector(5.0, 7.0, 9.0), result)
    }

    @Test
    fun `minus operator should subtract vectors properly`() {
        val v1 = Vector(4, 5, 6)
        val v2 = Vector(1, 2, 3)
        val result = v1 - v2
        Assertions.assertEquals(Vector(3.0, 3.0, 3.0), result)
    }

    @Test
    fun `times operator should multiply vector by scalar`() {
        val v = Vector(1, 2, 3)
        val result = v * 2
        Assertions.assertEquals(Vector(2.0, 4.0, 6.0), result)
    }

    @Test
    fun `div operator should divide vector by scalar`() {
        val v = Vector(2, 4, 6)
        val result = v / 2
        Assertions.assertEquals(Vector(1.0, 2.0, 3.0), result)
    }

    @Test
    fun `div operator should throw exception on division by zero`() {
        val v = Vector(1, 2, 3)
        val exception = assertThrows<IllegalArgumentException> {
            v / 0
        }
        Assertions.assertEquals("Division by zero is not allowed", exception.message)
    }

    @Test
    fun `dot function should calculate dot product correctly for positive vectors`() {
        val v1 = Vector(1, 2, 3)
        val v2 = Vector(4, 5, 6)
        val result = v1 dot v2
        Assertions.assertEquals(32.0, result) // 1*4 + 2*5 + 3*6 = 32
    }

    @Test
    fun `dot function should calculate dot product correctly for vectors with both positive and negative values`() {
        val v1 = Vector(1, -2, 3)
        val v2 = Vector(-4, 5, -6)
        val result = v1 dot v2
        Assertions.assertEquals(-32.0, result) // 1*(-4) + (-2)*5 + 3*(-6) = -32
    }

    @Test
    fun `dot function should calculate dot product correctly for vectors with zero values`() {
        val v1 = Vector(1, 0, 3)
        val v2 = Vector(0, 5, 0)
        val result = v1 dot v2
        Assertions.assertEquals(0.0, result) // 1*0 + 0*5 + 3*0 = 0
    }

    @Test
    fun `dot function should return zero when one vector is a zero vector`() {
        val v1 = Vector(0, 0, 0)
        val v2 = Vector(4, 5, 6)
        val result = v1 dot v2
        Assertions.assertEquals(0.0, result) // 0*4 + 0*5 + 0*6 = 0
    }

    @Test
    fun `cross function should calculate cross product of two non-zero vectors correctly`() {
        val v1 = Vector(1, 0, 0)
        val v2 = Vector(0, 1, 0)
        val result = v1 cross v2
        Assertions.assertEquals(Vector(0.0, 0.0, 1.0), result) // (1,0,0) x (0,1,0) = (0,0,1)
    }

    @Test
    fun `cross function should return zero vector when vectors are parallel`() {
        val v1 = Vector(1, 1, 1)
        val v2 = Vector(2, 2, 2)
        val result = v1 cross v2
        Assertions.assertEquals(Vector(0.0, 0.0, 0.0), result) // Parallel vectors give (0,0,0)
    }

    @Test
    fun `cross function should return zero vector when one vector is a zero vector`() {
        val v1 = Vector(0, 0, 0)
        val v2 = Vector(1, 2, 3)
        val result = v1 cross v2
        Assertions.assertEquals(Vector(0.0, 0.0, 0.0), result) // Zero vector x (1,2,3) = (0,0,0)
    }

    @Test
    fun `cross function should calculate cross product correctly for vectors with positive and negative components`() {
        val v1 = Vector(1, -2, 3)
        val v2 = Vector(-4, 5, -6)
        val result = v1 cross v2
        Assertions.assertEquals(Vector(-3.0, -6.0, -3.0), result) // ((-2)*(-6) - (3)*(5), ..., ...)
    }

    @Test
    fun `unaryMinus should negate vector coordinates properly`() {
        val v = Vector(1.0, 2.0, 3.0)
        val result = -v
        Assertions.assertEquals(Vector(-1.0, -2.0, -3.0), result)
    }

    @Test
    fun `unaryMinus should negate vector coordinates with positive and negative values`() {
        val v = Vector(1.0, -2.0, 3.0)
        val result = -v
        Assertions.assertEquals(Vector(-1.0, 2.0, -3.0), result)
    }

    @Test
    fun `unaryMinus should negate a zero vector correctly`() {
        val v = Vector(0.0, 0.0, 0.0)
        val result = -v
        Assertions.assertEquals(Vector(0.0, 0.0, 0.0), result)
    }

    @Test
    fun `hashCode should be equal for identical vectors`() {
        val v1 = Vector(1.0, 2.0, 3.0)
        val v2 = Vector(1.0, 2.0, 3.0)
        Assertions.assertEquals(v1.hashCode(), v2.hashCode())
    }

    @Test
    fun `hashCode should differ for distinct vectors`() {
        val v1 = Vector(1.0, 2.0, 3.0)
        val v2 = Vector(4.0, 5.0, 6.0)
        Assertions.assertNotEquals(v1.hashCode(), v2.hashCode())
    }

    @Test
    fun `hashCode should be consistent for the same vector instance`() {
        val v = Vector(1.0, 2.0, 3.0)
        val initialHashCode = v.hashCode()
        Assertions.assertEquals(initialHashCode, v.hashCode())
    }


    @Test
    fun `squaredLength should calculate correctly for positive components`() {
        val vector = Vector(3.0, 4.0, 0.0)
        assertEquals(25.0, vector.squaredLength) // 3² + 4² + 0² = 9 + 16 + 0 = 25
    }

    @Test
    fun `squaredLength should calculate correctly for negative components`() {
        val vector = Vector(-3.0, -4.0, 0.0)
        assertEquals(25.0, vector.squaredLength) // (-3)² + (-4)² + 0² = 9 + 16 + 0 = 25
    }

    @Test
    fun `squaredLength should calculate correctly for mixed positive and negative components`() {
        val vector = Vector(1.0, -2.0, 3.0)
        assertEquals(14.0, vector.squaredLength) // 1² + (-2)² + 3² = 1 + 4 + 9 = 14
    }

    @Test
    fun `squaredLength should return zero for zero vector`() {
        val vector = Vector(0.0, 0.0, 0.0)
        assertEquals(0.0, vector.squaredLength)
    }

    @Test
    fun `squaredLength should be lazy evaluated and cached`() {
        val vector = Vector(3.0, 4.0, 0.0)
        val firstAccess = vector.squaredLength
        val secondAccess = vector.squaredLength
        assertEquals(firstAccess, secondAccess)
        assertEquals(25.0, firstAccess)
    }

    @Test
    fun `length should calculate correctly for 3-4-5 triangle`() {
        val vector = Vector(3.0, 4.0, 0.0)
        assertEquals(5.0, vector.length, 0.0001) // sqrt(25) = 5
    }

    @Test
    fun `length should calculate correctly for unit vector components`() {
        val vector = Vector(1.0, 0.0, 0.0)
        assertEquals(1.0, vector.length, 0.0001)
    }

    @Test
    fun `length should calculate correctly for negative components`() {
        val vector = Vector(-3.0, -4.0, 0.0)
        assertEquals(5.0, vector.length, 0.0001) // sqrt(9 + 16 + 0) = 5
    }

    @Test
    fun `length should return zero for zero vector`() {
        val vector = Vector(0.0, 0.0, 0.0)
        assertEquals(0.0, vector.length, 0.0001)
    }

    @Test
    fun `length should calculate correctly for fractional components`() {
        val vector = Vector(0.5, 0.5, 0.5)
        val expected = sqrt(0.75) // sqrt(0.25 + 0.25 + 0.25)
        assertEquals(expected, vector.length, 0.0001)
    }

    @Test
    fun `length should be lazy evaluated and cached`() {
        val vector = Vector(3.0, 4.0, 0.0)
        val firstAccess = vector.length
        val secondAccess = vector.length
        assertEquals(firstAccess, secondAccess)
        assertEquals(5.0, firstAccess, 0.0001)
    }

    @Test
    fun `unit should create normalized vector for standard vector`() {
        val vector = Vector(3.0, 4.0, 0.0)
        val unitVector = vector.unit
        assertEquals(0.6, unitVector.x, 0.0001) // 3/5
        assertEquals(0.8, unitVector.y, 0.0001) // 4/5
        assertEquals(0.0, unitVector.z, 0.0001) // 0/5
    }

    @Test
    fun `unit should have length of 1 for non-zero vector`() {
        val vector = Vector(5.0, 12.0, 0.0)
        val unitVector = vector.unit
        assertEquals(1.0, unitVector.length, 0.0001)
    }

    @Test
    fun `unit should preserve direction for positive components`() {
        val vector = Vector(2.0, 2.0, 2.0)
        val unitVector = vector.unit
        val expected = 1.0 / sqrt(3.0) // Each component should be 2/sqrt(12) = 2/(2*sqrt(3)) = 1/sqrt(3)
        assertEquals(expected, unitVector.x, 0.0001)
        assertEquals(expected, unitVector.y, 0.0001)
        assertEquals(expected, unitVector.z, 0.0001)
    }

    @Test
    fun `unit should preserve direction for negative components`() {
        val vector = Vector(-3.0, -4.0, 0.0)
        val unitVector = vector.unit
        assertEquals(-0.6, unitVector.x, 0.0001) // -3/5
        assertEquals(-0.8, unitVector.y, 0.0001) // -4/5
        assertEquals(0.0, unitVector.z, 0.0001) // 0/5
    }

    @Test
    fun `unit should work correctly for unit vectors`() {
        val vector = Vector(1.0, 0.0, 0.0)
        val unitVector = vector.unit
        assertEquals(1.0, unitVector.x, 0.0001)
        assertEquals(0.0, unitVector.y, 0.0001)
        assertEquals(0.0, unitVector.z, 0.0001)
    }

    @Test
    fun `unit should be lazy evaluated and cached`() {
        val vector = Vector(3.0, 4.0, 0.0)
        val firstAccess = vector.unit
        val secondAccess = vector.unit
        assertEquals(firstAccess, secondAccess)
        assertEquals(0.6, firstAccess.x, 0.0001)
    }

    @Test
    fun `unit should throw exception for zero vector`() {
        val vector = Vector(0.0, 0.0, 0.0)
        assertThrows<IllegalArgumentException> {
            vector.unit
        }
    }

    @Test
    fun `unit vector should maintain dot product relationship`() {
        val vector = Vector(3.0, 4.0, 5.0)
        val unitVector = vector.unit

        // The unit vector should be parallel to the original, so their dot product
        // should equal the length of the original vector
        val dotProduct = vector dot unitVector
        assertEquals(vector.length, dotProduct, 0.0001)
    }

    @Test
    fun `squaredLength should equal length squared`() {
        val vector = Vector(3.0, 4.0, 5.0)
        assertEquals(vector.squaredLength, vector.length * vector.length, 0.0001)
    }
}