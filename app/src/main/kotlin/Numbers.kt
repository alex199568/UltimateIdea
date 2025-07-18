package org.example.app

import kotlin.math.absoluteValue

const val Epsilon = 1e-16

infix fun Number.eq(other: Number): Boolean {
    return (toDouble() - other.toDouble()).absoluteValue < Epsilon
}
