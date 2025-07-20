package org.example.app

import org.example.app.linalg.Point
import org.example.app.linalg.Vector
import kotlin.test.Test
import kotlin.test.assertEquals

class RayTest {

    @Test
    fun testDefaultRay() {
        val ray = Ray()
        assertEquals(Point.zero, ray.origin)
        assertEquals(Vector.zero, ray.direction)
    }

    @Test
    fun testRayAtT() {
        val ray = Ray(Point(1, 2, 3), Vector(1, 0, 0))
        val pointAtT = ray[2]
        assertEquals(Point(3, 2, 3), pointAtT)
    }
}
