package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ImageTest {

    /**
     * Tests for the Image class which represents a 2D grid of colors.
     * Verifies functionality for indexing, getting, and setting pixel colors.
     */

    @Test
    fun testIndexWithinBounds() {
        val image = Image(3, 3)
        val expectedIndex = 4  // For coordinates (1, 1) in a 3x3 grid
        val actualIndex = image.index(1, 1)
        assertEquals(expectedIndex, actualIndex, "Index does not match expected value.")
    }

    @Test
    fun testIndexOutOfBoundsX() {
        val image = Image(3, 3)
        assertFailsWith<IllegalArgumentException> {
            image.index(3, 1)
        }
    }

    @Test
    fun testIndexOutOfBoundsY() {
        val image = Image(3, 3)
        assertFailsWith<IllegalArgumentException> {
            image.index(1, 3)
        }
    }

    @Test
    fun testGetColorWithinBounds() {
        val image = Image(3, 3)
        val expectedColor = Color.black
        val actualColor = image[1, 1]
        assertEquals(expectedColor, actualColor, "Retrieved color does not match expected color.")
    }

    @Test
    fun testGetColorOutOfBoundsX() {
        val image = Image(3, 3)
        assertFailsWith<IllegalArgumentException> {
            image[3, 1]
        }
    }

    @Test
    fun testGetColorOutOfBoundsY() {
        val image = Image(3, 3)
        assertFailsWith<IllegalArgumentException> {
            image[1, 3]
        }
    }

    @Test
    fun testSetColorWithinBounds() {
        val image = Image(3, 3)
        val newColor = Color.red
        image[2, 2] = newColor
        val actualColor = image[2, 2]
        assertEquals(newColor, actualColor, "Set color does not match retrieved color.")
    }

    @Test
    fun testSetColorOutOfBoundsX() {
        val image = Image(3, 3)
        val newColor = Color.blue
        assertFailsWith<IllegalArgumentException> {
            image[3, 2] = newColor
        }
    }

    @Test
    fun testSetColorOutOfBoundsY() {
        val image = Image(3, 3)
        val newColor = Color.green
        assertFailsWith<IllegalArgumentException> {
            image[2, 3] = newColor
        }
    }
}