package org.example.app.org.example.app

import org.example.app.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
}