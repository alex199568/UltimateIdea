package org.example.app

import org.example.app.linalg.Affine
import kotlin.math.sqrt

class Sphere(
    tr: Affine = Affine.identity
) {

    val trInverse = tr.inverse

    fun localIntersect(ray: Ray): List<Intersection> {
        val sphereToRay = ray.origin.vector
        val a = ray.direction.dot(ray.direction)
        val b = 2.0 * ray.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - 1.0
        val discriminant = b * b - 4.0 * a * c

        return when {
            discriminant < 0 -> emptyList()
            discriminant == 0.0 -> listOf(Intersection(this, -b / (2.0 * a)))
            else -> {
                val t1 = (-b - sqrt(discriminant)) / (2.0 * a)
                val t2 = (-b + sqrt(discriminant)) / (2.0 * a)
                listOf(Intersection(this, t1), Intersection(this, t2))
            }
        }
    }

    fun intersect(ray: Ray): List<Intersection> {
        val localRay = trInverse * ray
        return localIntersect(localRay)
    }
}
