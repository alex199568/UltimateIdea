package org.example.app.linalg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun `times should correctly multiply a non-trivial 4x4 matrix with a vector`() {
        val matrix = Transform(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 16
        )
        val vector = Vector(
            x = 1,
            y = 2,
            z = 3
        )
        val result = matrix * vector

        assertEquals(14.0, result.x)
        assertEquals(38.0, result.y)
        assertEquals(62.0, result.z)
    }

    @Test
    fun `multiply a 4x4 matrix with point using complex transformation`() {
        // Define a 4x4 matrix with combined transformations:
        // Scaling (2x, 3y, 1.5z), a translation (+4x, -5y, +6z), and rotation about z-axis (45 degrees).
        val matrix = Transform(
            1.414, -1.414, 0.0, 4.0,   // Combined rotation and translation (row 0)
            1.414, 1.414, 0.0, -5.0,  // Combined rotation and translation (row 1)
            0.0, 0.0, 1.5, 6.0,   // Scaling along z-axis (row 2)
            0.0, 0.0, 0.0, 1.0    // Homogeneous coordinate row
        )

        // Define a point (x=2, y=3, z=1)
        val point = Point(2.0, 3.0, 1.0)

        // Perform matrix multiplication
        val result = matrix * point

        // Expected calculation:
        // x' = (1.414 * 2) + (-1.414 * 3) + (0 * 1) + (4)   = 2.828 - 4.242 + 4 = 2.586
        // y' = (1.414 * 2) + ( 1.414 * 3) + (0 * 1) + (-5)  = 2.828 + 4.242 - 5 = 2.07
        // z' = (0 * 2) + (0 * 3) + (1.5 * 1) + (6)         = 0 + 0 + 1.5 + 6 = 7.5
        // w' = (0 * 2) + (0 * 3) + (0 * 1) + (1)           = 1
        // Result as Point: (x'/w', y'/w', z'/w') = (2.586, 2.07, 7.5)

        assertEquals(Point(2.586, 2.07, 7.5), result, "The matrix multiplication did not yield the expected point.")
    }


    @Test
    fun `times should correctly multiply a 4x4 matrix with a point, (2)`() {
        val matrix = Transform(
            1, 0, 0, 2,
            0, 1, 0, 3,
            0, 0, 1, 4,
            0, 0, 0, 1
        ) // Translation matrix

        val point = Point(1, 1, 1)

        val result = matrix * point
        println(result) // Should output: Point(3.0, 4.0, 5.0)

    }

    @Test
    fun `times should correctly multiply identity matrix with a point`() {
        val identity = Transform(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        )
        val point = Point(2, 3, 4)
        val result = identity * point

        assertEquals(point, result)
    }
}
