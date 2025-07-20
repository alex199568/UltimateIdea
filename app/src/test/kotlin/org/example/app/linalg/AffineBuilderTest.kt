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
    fun `moveX adds to current X translation`() {
        val builder = AffineBuilder().moveX(5)
        val result = builder.build()
        assertEquals(Affine.translate(5, 0, 0), result)
    }

    @Test
    fun `moveLeft subtracts from current X translation`() {
        val builder = AffineBuilder().moveLeft(5)
        val result = builder.build()
        assertEquals(Affine.translate(-5, 0, 0), result)
    }

    @Test
    fun `moveRight adds to current X translation`() {
        val builder = AffineBuilder().moveRight(5)
        val result = builder.build()
        assertEquals(Affine.translate(5, 0, 0), result)
    }

    @Test
    fun `moveUp adds to current Y translation`() {
        val builder = AffineBuilder().moveUp(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, 5, 0), result)
    }

    @Test
    fun `moveDown subtracts from current Y translation`() {
        val builder = AffineBuilder().moveDown(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, -5, 0), result)
    }

    @Test
    fun `moveForward adds to current Z translation`() {
        val builder = AffineBuilder().moveForward(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, 0, 5), result)
    }

    @Test
    fun `moveBackward subtracts from current Z translation`() {
        val builder = AffineBuilder().moveBackward(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, 0, -5), result)
    }

    @Test
    fun `moveY adds to current Y translation`() {
        val builder = AffineBuilder().moveY(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, 5, 0), result)
    }

    @Test
    fun `moveZ adds to current Z translation`() {
        val builder = AffineBuilder().moveZ(5)
        val result = builder.build()
        assertEquals(Affine.translate(0, 0, 5), result)
    }

    @Test
    fun `rotateX applies rotation around X-axis`() {
        val builder = AffineBuilder().rotateX(Math.PI / 4)
        val result = builder.build()
        assertEquals(Affine.rotateX(Math.PI / 4), result)
    }

    @Test
    fun `build applies X rotation after translation correctly`() {
        val builder = AffineBuilder()
            .translate(2, 3, 1)
            .rotateX(Math.PI / 2)

        val result = builder.build()

        // Expected: Translation matrix * RotationX matrix
        // This follows the T * R transformation order
        val expected = Affine.translate(2, 3, 1) * Affine.rotateX(Math.PI / 2)

        assertEquals(expected, result)
    }


    @Test
    fun `rotateXd applies rotation around X-axis in degrees`() {
        val builder = AffineBuilder().rotateXd(45)
        val result = builder.build()
        assertEquals(Affine.rotateX(Math.PI / 4), result)
    }

    @Test
    fun `rotateY applies rotation around Y-axis`() {
        val builder = AffineBuilder().rotateY(Math.PI / 4)
        val result = builder.build()
        assertEquals(Affine.rotateY(Math.PI / 4), result)
    }

    @Test
    fun `rotateYd applies rotation around Y-axis in degrees`() {
        val builder = AffineBuilder().rotateYd(45)
        val result = builder.build()
        assertEquals(Affine.rotateY(Math.PI / 4), result)
    }

    @Test
    fun `rotateZ applies rotation around Z-axis`() {
        val builder = AffineBuilder().rotateZ(Math.PI / 4)
        val result = builder.build()
        assertEquals(Affine.rotateZ(Math.PI / 4), result)
    }

    @Test
    fun `rotateZd applies rotation around Z-axis in degrees`() {
        val builder = AffineBuilder().rotateZd(45)
        val result = builder.build()
        assertEquals(Affine.rotateZ(Math.PI / 4), result)
    }

    @Test
    fun `shrinkX divides the X scaling factor`() {
        val builder = AffineBuilder().scaleX(4).shrinkX(2)
        val result = builder.build()
        assertEquals(Affine.scale(2, 1, 1), result)
    }

    @Test
    fun `grow scales uniformly in all axes`() {
        val builder = AffineBuilder().grow(2, 2, 2)
        val result = builder.build()
        assertEquals(Affine.scale(2, 2, 2), result)
    }

    @Test
    fun `shrink scales uniformly in all axes`() {
        val builder = AffineBuilder().scale(6, 6, 6).shrink(2, 2, 2)
        val result = builder.build()
        assertEquals(Affine.scale(3, 3, 3), result)
    }

    @Test
    fun `shrink scales non-uniformly in specific axes`() {
        val builder = AffineBuilder().scale(8, 9, 10).shrink(2, 3, 5)
        val result = builder.build()
        assertEquals(Affine.scale(4, 3, 2), result)
    }

    @Test
    fun `shrink combines correctly with other transformations`() {
        val builder = AffineBuilder().translate(2, 3, -1).shrink(2, 4, 8)
        val result = builder.build()
        val expected = Affine.translate(2, 3, -1) * Affine.scale(0.5, 0.25, 0.125)
        assertEquals(expected, result)
    }

    @Test
    fun `grow scales non-uniformly in specific axes`() {
        val builder = AffineBuilder().grow(2, 3, 4)
        val result = builder.build()
        assertEquals(Affine.scale(2, 3, 4), result)
    }

    @Test
    fun `grow combines correctly with other transformations`() {
        val builder = AffineBuilder().translate(5, -3, 2).grow(2, 3, 4)
        val result = builder.build()
        val expected = Affine.translate(5, -3, 2) * Affine.scale(2, 3, 4)
        assertEquals(expected, result)
    }

    @Test
    fun `shrinkY divides the Y scaling factor`() {
        val builder = AffineBuilder().scaleY(6).shrinkY(2)
        val result = builder.build()
        assertEquals(Affine.scale(1, 3, 1), result)
    }

    @Test
    fun `shrinkZ divides the Z scaling factor`() {
        val builder = AffineBuilder().scaleZ(8).shrinkZ(2)
        val result = builder.build()
        assertEquals(Affine.scale(1, 1, 4), result)
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

    @Test
    fun `growX scales the X axis correctly`() {
        val builder = AffineBuilder().growX(3)
        val result = builder.build()
        assertEquals(Affine.scale(3, 1, 1), result)
    }

    @Test
    fun `growX combines correctly with other transformations`() {
        val builder = AffineBuilder().translate(2, 3, 4).growX(2)
        val result = builder.build()
        val expected = Affine.translate(2, 3, 4) * Affine.scale(2, 1, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `growY scales the Y axis correctly`() {
        val builder = AffineBuilder().growY(3)
        val result = builder.build()
        assertEquals(Affine.scale(1, 3, 1), result)
    }

    @Test
    fun `growY combines correctly with other transformations`() {
        val builder = AffineBuilder().translate(2, 3, 4).growY(2)
        val result = builder.build()
        val expected = Affine.translate(2, 3, 4) * Affine.scale(1, 2, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `growZ scales the Z axis correctly`() {
        val builder = AffineBuilder().growZ(3)
        val result = builder.build()
        assertEquals(Affine.scale(1, 1, 3), result)
    }

    @Test
    fun `growZ combines correctly with other transformations`() {
        val builder = AffineBuilder().translate(2, 3, 4).growZ(2)
        val result = builder.build()
        val expected = Affine.translate(2, 3, 4) * Affine.scale(1, 1, 2)
        assertEquals(expected, result)
    }

    @Test
    fun `shear applies shearing transformations correctly`() {
        val builder = AffineBuilder().shear(
            sx = 1.0, sy = 2.0,
            yx = 0.5, yz = -0.5,
            zx = 1.5, zy = 0.0
        )
        val result = builder.build()
        val expected = Affine.shearing(
            1.0, 2.0,
            0.5, -0.5,
            1.5, 0.0
        )
        assertEquals(expected, result)
    }

    @Test
    fun `shear does not affect result when all coefficients are zero`() {
        val builder = AffineBuilder().shear(
            sx = 0.0, sy = 0.0,
            yx = 0.0, yz = 0.0,
            zx = 0.0, zy = 0.0
        )
        val result = builder.build()
        assertEquals(Affine.identity, result)
    }

    @Test
    fun `shear combines correctly with scaling and translation`() {
        val builder = AffineBuilder()
            .translate(3, 4, 5)
            .scale(2, 2, 2)
            .shear(
                sx = 0.5, sy = -0.5,
                yx = 1.0, yz = 0.0,
                zx = 0.0, zy = 1.5
            )
        val result = builder.build()
        val expected = Affine.translate(3, 4, 5) *
                Affine.shearing(0.5, -0.5, 1.0, 0.0, 0.0, 1.5) *
                Affine.scale(2, 2, 2)
        assertEquals(expected, result)
    }

    @Test
    fun `shear with mixed values works as expected`() {
        val builder = AffineBuilder().shear(
            sx = -1.0, sy = 1.0,
            yx = 2.0, yz = -2.0,
            zx = 0.5, zy = -0.5
        )
        val result = builder.build()
        val expected = Affine.shearing(
            -1.0, 1.0,
            2.0, -2.0,
            0.5, -0.5
        )
        assertEquals(expected, result)
    }
}