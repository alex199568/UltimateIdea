package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ColorTest {

    /**
     * Color class represents a color with red, green, and blue components,
     * and supports operations such as addition, subtraction, multiplication, and division.
     */

    @Test
    fun `test addition of two Color objects`() {
        // Given
        val color1 = Color(0.5, 0.7, 0.2)
        val color2 = Color(0.3, 0.2, 0.4)

        // When
        val result = color1 + color2

        // Then
        assertEquals(Color(0.8, 0.9, 0.6), result)
    }

    @Test
    fun `test subtraction of two Color objects`() {
        // Given
        val color1 = Color(0.6, 0.8, 0.5)
        val color2 = Color(0.4, 0.3, 0.2)

        // When
        val result = color1 - color2

        // Then
        assertEquals(Color(0.2, 0.5, 0.3), result)
    }

    @Test
    fun `test multiplication of Color with a scalar`() {
        // Given
        val color = Color(0.5, 0.6, 0.7)
        val scalar = 2.0

        // When
        val result = color * scalar

        // Then
        assertEquals(Color(1.0, 1.2, 1.4), result)
    }

    @Test
    fun `test multiplication of two Color objects`() {
        // Given
        val color1 = Color(0.5, 0.6, 0.7)
        val color2 = Color(0.4, 0.3, 0.2)

        // When
        val result = color1 * color2

        // Then
        assertEquals(Color(0.2, 0.18, 0.14), result)
    }

    @Test
    fun `test division of Color by a scalar`() {
        // Given
        val color = Color(0.8, 0.6, 0.4)
        val scalar = 2.0

        // When
        val result = color / scalar

        // Then
        assertEquals(Color(0.4, 0.3, 0.2), result)
    }

    @Test
    fun `test division of Color by zero throws exception`() {
        // Given
        val color = Color(1.0, 1.0, 1.0)
        val scalar = 0.0

        // Then
        assertFailsWith<IllegalArgumentException> {
            // When
            color / scalar
        }
    }

    @Test
    fun `test creation of Color from numbers`() {
        // When
        val color = Color(1, 2, 3)

        // Then
        assertEquals(Color(1.0, 2.0, 3.0), color)
    }

    @Test
    fun `test RGB conversion for valid Color values`() {
        // Given
        val color = Color(0.5, 0.75, 1.0)

        // When
        val result = color.rgb

        // Then
        assertEquals(0xFF7FBFFF.toInt(), result)
    }

    @Test
    fun `test RGB conversion clamps invalid Color values`() {
        // Given
        val color = Color(-0.1, 1.2, 0.5)

        // When
        val result = color.rgb

        // Then
        assertEquals(0xFF00FF7F.toInt(), result) // Red clamped to 0, Green clamped to 255, Blue is valid
    }

    @Test
    fun `test hashCode consistency`() {
        // Given
        val color1 = Color(0.5, 0.5, 0.5)
        val color2 = Color(0.5, 0.5, 0.5)

        // Then
        assertEquals(color1.hashCode(), color2.hashCode())
    }

    @Test
    fun `test hashCode differentiation`() {
        // Given
        val color1 = Color(0.5, 0.5, 0.5)
        val color2 = Color(0.6, 0.6, 0.6)

        // Then
        assert(color1.hashCode() != color2.hashCode()) {
            "Expected different hash codes for different colors"
        }
    }
}