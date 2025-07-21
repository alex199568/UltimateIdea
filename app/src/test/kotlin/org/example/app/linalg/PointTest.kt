package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

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

    @Test
    fun `test hashCode consistency for the same Point instance`() {
        val point = Point(1.5, 2.5, 3.5)
        val hash1 = point.hashCode()
        val hash2 = point.hashCode()
        assertEquals(hash1, hash2, "Expected hashCode to be consistent for the same Point instance")
    }

    @Test
    fun `test hashCode equality for equal Points`() {
        val point1 = Point(1.5, 2.5, 3.5)
        val point2 = Point(1.5, 2.5, 3.5)
        assertEquals(point1.hashCode(), point2.hashCode(), "Expected equal Points to have the same hashCode")
    }

    @Test
    fun `test hashCode inequality for different Points`() {
        val point1 = Point(1.5, 2.5, 3.5)
        val point2 = Point(4.5, 5.5, 6.5)
        assertNotEquals(point1.hashCode(), point2.hashCode(), "Expected different Points to have different hashCodes")
    }

    @Test
    fun `test vector property returns correct Vector`() {
        val point = Point(1.5, 2.5, 3.5)
        val vector = point.vector
        assertEquals(1.5, vector.x, "Expected x to match the Point's x coordinate")
        assertEquals(2.5, vector.y, "Expected y to match the Point's y coordinate")
        assertEquals(3.5, vector.z, "Expected z to match the Point's z coordinate")
    }

    @Test
    fun `test vector property of Point zero returns Vector zero`() {
        val vector = Point.zero.vector
        assertEquals(Vector.zero, vector, "Expected Point.zero's vector to equal Vector.zero")
    }

    @Test
    fun `test vector property consistency for the same Point instance`() {
        val point = Point(4.0, 5.0, 6.0)
        val vector1 = point.vector
        val vector2 = point.vector
        assertEquals(vector1, vector2, "Expected vector property to be consistent for the same Point instance")
    }
}