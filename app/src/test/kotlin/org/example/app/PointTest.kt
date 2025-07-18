package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals

class PointTest {

    /**
     * Tests for the `Point` class.
     *
     * The `Point` class represents a point in a 3D Cartesian coordinate system with double precision.
     * This class provides:
     * 1. Primary constructor and secondary constructor for initialization with numbers.
     * 2. Overloaded operators for addition and subtraction with `Vector` objects.
     * 3. Subtraction operator to calculate the vector between two points.
     */

    @Test
    fun `test creation of Point using primary constructor`() {
        val point = Point(1.5, 2.5, 3.5)
        assertEquals(1.5, point.x, "Expected x to be 1.5")
        assertEquals(2.5, point.y, "Expected y to be 2.5")
        assertEquals(3.5, point.z, "Expected z to be 3.5")
    }

    @Test
    fun `test creation of Point using secondary constructor`() {
        val point = Point(1, 2, 3)
        assertEquals(1.0, point.x, "Expected x to be 1.0 after conversion")
        assertEquals(2.0, point.y, "Expected y to be 2.0 after conversion")
        assertEquals(3.0, point.z, "Expected z to be 3.0 after conversion")
    }

    @Test
    fun `test addition of Point and Vector`() {
        val point = Point(1.0, 1.0, 1.0)
        val vector = Vector(2.0, 3.0, 4.0)
        val result = point + vector
        assertEquals(3.0, result.x, "Expected x to be 3.0 after addition")
        assertEquals(4.0, result.y, "Expected y to be 4.0 after addition")
        assertEquals(5.0, result.z, "Expected z to be 5.0 after addition")
    }

    @Test
    fun `test subtraction of Vector from Point`() {
        val point = Point(4.0, 5.0, 6.0)
        val vector = Vector(1.0, 2.0, 3.0)
        val result = point - vector
        assertEquals(3.0, result.x, "Expected x to be 3.0 after subtraction")
        assertEquals(3.0, result.y, "Expected y to be 3.0 after subtraction")
        assertEquals(3.0, result.z, "Expected z to be 3.0 after subtraction")
    }

    @Test
    fun `test subtraction of two Points to get a Vector`() {
        val point1 = Point(3.0, 4.0, 5.0)
        val point2 = Point(1.0, 1.0, 1.0)
        val result = point1 - point2
        assertEquals(2.0, result.x, "Expected x to be 2.0 after subtraction")
        assertEquals(3.0, result.y, "Expected y to be 3.0 after subtraction")
        assertEquals(4.0, result.z, "Expected z to be 4.0 after subtraction")
    }
}