package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LinAlgKtTest {

    /**
     * Tests for the `transform` function in the `LinAlgKt` class.
     *
     * The `transform` function applies a series of transformations defined in the `AffineBuilder`
     * and constructs an `Affine` transformation matrix.
     */

    @Test
    fun `test identity transform`() {
        val result = transform {}

        val expected = Affine.identity
        assertEquals(expected, result, "Transform with no operations should return identity matrix")
    }

    @Test
    fun `test translation transform`() {
        val result = transform {
            translate(3, 4, 5)
        }

        val expected = Affine.translate(3, 4, 5)
        assertEquals(expected, result, "Transform with translation should match translation matrix")
    }

    @Test
    fun `test rotation transform`() {
        val result = transform {
            rotateX(Math.PI / 2)
        }

        val expected = Affine.rotateX(Math.PI / 2)
        assertEquals(expected, result, "Transform with rotation around X-axis should match the rotation matrix")
    }

    @Test
    fun `test scaling transform`() {
        val result = transform {
            scale(2, 3, 4)
        }

        val expected = Affine.scale(2, 3, 4)
        assertEquals(expected, result, "Transform with scaling should match scaling matrix")
    }

    @Test
    fun `test shearing transform`() {
        val result = transform {
            shear(1, 2, 3, 4, 5, 6)
        }

        val expected = Affine.shearing(1, 2, 3, 4, 5, 6)
        assertEquals(expected, result, "Transform with shearing should match shearing matrix")
    }

    @Test
    fun `test chained transforms`() {
        val result = transform {
            translate(1, 2, 3)
            rotateZ(Math.PI / 4)
            scale(2, 2, 2)
        }

        val expected = Affine.translate(1, 2, 3) *
                Affine.rotateZ(Math.PI / 4) *
                Affine.scale(2, 2, 2)

        assertEquals(expected, result, "Chained transforms should produce the correct resulting matrix")
    }

    @Test
    fun `test invalid transform`() {
        assertFailsWith<IllegalArgumentException> {
            transform {
                // Intentionally adding invalid operations for builder
                shrink(0, 0, 0)
            }
        }
    }
}