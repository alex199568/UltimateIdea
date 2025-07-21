package org.example.app

import org.example.app.linalg.Affine
import org.example.app.linalg.Point
import org.example.app.linalg.Vector
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SphereTest {

    @Test
    fun testIntersectWithRayMissesSphere() {
        val sphere = Sphere()
        val ray = Ray(Point(0, 2, -5), Vector(0, 0, 1))
        val intersections = sphere.intersect(ray)
        assertTrue(intersections.isEmpty())
    }

    @Test
    fun testIntersectWithRayTangential() {
        val sphere = Sphere()
        val ray = Ray(Point(0, 1, -5), Vector(0, 0, 1))
        val intersections = sphere.intersect(ray)
        assertEquals(1, intersections.size)
        assertEquals(5.0, intersections[0].t, 0.001)
    }

    @Test
    fun testIntersectWithRayIntersectsSphere() {
        val sphere = Sphere()
        val ray = Ray(Point(0, 0, -5), Vector(0, 0, 1))
        val intersections = sphere.intersect(ray)
        assertEquals(2, intersections.size)
        assertEquals(4.0, intersections[0].t, 0.001)
        assertEquals(6.0, intersections[1].t, 0.001)
    }

    @Test
    fun testIntersectWithTransformedSphere() {
        val transform = Affine.translate(1, 0, 0)
        val sphere = Sphere(transform)
        val ray = Ray(Point(0, 0, -5), Vector(0, 0, 1))
        val intersections = sphere.intersect(ray)

        // The transformed ray goes from (-1, 0, -5) to (-1, 0, 0) which touches the unit sphere
        assertEquals(1, intersections.size)
        assertEquals(5.0, intersections[0].t, 0.001)
    }


    @Test
    fun testRayTransformationInverseTranslation() {
        // Create a translation matrix that moves +1 in X direction
        val translation = Affine.translate(1, 0, 0)
        val inverseTranslation = translation.inverse

        // Ray that should hit a sphere at origin after inverse translation
        val ray = Ray(Point(1, 0, -5), Vector(0, 0, 1))

        // Transform ray with inverse (should move ray origin from (1,0,-5) to (0,0,-5))
        val transformedRay = inverseTranslation * ray

        // The transformed ray should now have origin at (0,0,-5) which should hit sphere at origin
        assertEquals(Point(0, 0, -5), transformedRay.origin)
        assertEquals(Vector(0, 0, 1), transformedRay.direction)
    }

    @Test
    fun testIntersectWithTransformedSphereMisses() {
        val transform = Affine.translate(2, 0, 0)  // Move sphere further away
        val sphere = Sphere(transform)
        val ray = Ray(Point(0, 0, -5), Vector(0, 0, 1))
        val intersections = sphere.intersect(ray)

        // Ray from (0,0,-5) becomes (-2,0,-5) after inverse transform
        // This ray misses the unit sphere since (-2,0,z) is always distance 2 from origin
        assertTrue(intersections.isEmpty())
    }
}
