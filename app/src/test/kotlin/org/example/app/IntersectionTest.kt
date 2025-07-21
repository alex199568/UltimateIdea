package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class IntersectionTest {

    @Test
    fun intersection() {
        val s = Sphere()
        val i = Intersection(s, 1)
        val expected = Intersection(s, 1)
        assertEquals(expected, i)
    }

    @Test
    fun `hashCode matches for equal Intersections`() {
        val sphere = Sphere()
        val i1 = Intersection(sphere, 2.5)
        val i2 = Intersection(sphere, 2.5)
        assertEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun `Intersection constructed using Number is equivalent`() {
        val sphere = Sphere()
        val intersection = Intersection(sphere, 2)
        assertEquals(Intersection(sphere, 2.0), intersection)
    }

    @Test
    fun `Intersections with different shapes or t are not equal`() {
        val sphere1 = Sphere()
        val sphere2 = Sphere()
        val i1 = Intersection(sphere1, 2.0)
        val i2 = Intersection(sphere2, 3.0)
        assertNotEquals(i1, i2)
    }
}
