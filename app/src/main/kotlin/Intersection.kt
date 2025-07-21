package org.example.app

class Intersection(
    val shape: Sphere,
    val t: Double
) {

    constructor(
        shape: Sphere,
        t: Number
    ) : this(shape, t.toDouble())

    override fun equals(other: Any?): Boolean {
        val i = other as? Intersection ?: return false
        return i.shape == shape && i.t == t
    }

    override fun hashCode(): Int {
        var result = t.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }
}
