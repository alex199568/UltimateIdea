package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals

class TransformTest {

    @Test
    fun `combine scaling and translation transformations with multiplication`() {
        // Define a Transform instance that scales (2x, 3y, 4z) and translates (+5x, +6y, +7z)
        val transform = Transform(
            2, 0, 0, 5,  // Scale X by 2, translate +5
            0, 3, 0, 6,  // Scale Y by 3, translate +6
            0, 0, 4, 7,  // Scale Z by 4, translate +7
            0, 0, 0, 1   // Homogeneous coordinate row
        )

        // Define a Point at (1, 1, 1)
        val point = Point(1, 1, 1)

        // Multiply the transformation with the point
        val result = transform * point

        // Compute the expected output manually:
        // x' = (2 * 1) + (0 * 1) + (0 * 1) + (5) = 2 + 0 + 0 + 5 = 7
        // y' = (0 * 1) + (3 * 1) + (0 * 1) + (6) = 0 + 3 + 0 + 6 = 9
        // z' = (0 * 1) + (0 * 1) + (4 * 1) + (7) = 0 + 0 + 4 + 7 = 11
        // w' = 1 (no change; homogeneous coordinate is 1)
        // Resulting Point: (7.0, 9.0, 11.0)

        assertEquals(Point(7.0, 9.0, 11.0), result, "The transformation did not produce the expected result.")
    }

}
