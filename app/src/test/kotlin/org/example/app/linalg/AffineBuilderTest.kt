package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals

class AffineBuilderTest {

    @Test
    fun `build returns identity matrix when no transformations are applied`() {
        val builder = AffineBuilder()
        val result = builder.build()
        assertEquals(Affine.identity, result)
    }

    @Test
    fun `build incorporates translation correctly`() {
        val builder = AffineBuilder().translate(5, -3, 2)
        val result = builder.build()
        assertEquals(Affine.translate(5, -3, 2), result)
    }

    @Test
    fun `build incorporates rotation correctly`() {
        val builder = AffineBuilder().rotateX(Math.PI / 4).rotateY(Math.PI / 3).rotateZ(Math.PI / 6)
        val result = builder.build()
        val expected = Affine.rotateX(Math.PI / 4) * Affine.rotateY(Math.PI / 3) * Affine.rotateZ(Math.PI / 6)
        assertEquals(expected, result)
    }

    @Test
    fun `build incorporates scaling correctly`() {
        val builder = AffineBuilder().scale(2, 3, 4)
        val result = builder.build()
        assertEquals(Affine.scale(2, 3, 4), result)
    }

    @Test
    fun `build combines multiple transformations correctly`() {
        val builder = AffineBuilder()
            .translate(3, -2, 5)
            .rotateZ(Math.PI / 2)
            .scale(1.5, 2.0, 0.5)

        val result = builder.build()
        val expected = Affine.translate(3, -2, 5) *
                Affine.rotateZ(Math.PI / 2) *
                Affine.scale(1.5, 2.0, 0.5)

        assertEquals(expected, result)
    }
}