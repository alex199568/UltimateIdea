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

    @Test
    fun testBufferedImageDimensions() {
        val image = Image(3, 4)
        val bufferedImage = image.buffered

        assertEquals(3, bufferedImage.width, "Buffered image width does not match.")
        assertEquals(4, bufferedImage.height, "Buffered image height does not match.")
    }

    @Test
    fun testBufferedImagePixelColors() {
        val image = Image(2, 2)
        image[0, 0] = Color.red
        image[1, 0] = Color.green
        image[0, 1] = Color.blue
        image[1, 1] = Color.black

        val bufferedImage = image.buffered

        assertEquals(Color.red.rgb, bufferedImage.getRGB(0, 0), "Top-left pixel does not match.")
        assertEquals(Color.green.rgb, bufferedImage.getRGB(1, 0), "Top-right pixel does not match.")
        assertEquals(Color.blue.rgb, bufferedImage.getRGB(0, 1), "Bottom-left pixel does not match.")
        assertEquals(Color.black.rgb, bufferedImage.getRGB(1, 1), "Bottom-right pixel does not match.")
    }

    @Test
    fun testBufferedImageUpdates() {
        val image = Image(2, 2)
        image[0, 0] = Color.red
        image[1, 1] = Color.green

        val bufferedImage = image.buffered

        // Update one color and verify the change in the buffered image
        image[1, 1] = Color.blue
        assertEquals(
            Color.blue.rgb,
            image.buffered.getRGB(1, 1),
            "Buffered image did not update after modifying image colors."
        )
    }

    @Test
    fun testSaveToFile() {
        val image = Image(3, 2)
        val path = "test_output.png"

        // Save the image
        image.save(path)

        // Read the saved image
        val savedImage = javax.imageio.ImageIO.read(java.io.File(path))

        // Verify dimensions
        assertEquals(3, savedImage.width, "Saved image width does not match.")
        assertEquals(2, savedImage.height, "Saved image height does not match.")

        // Clean up
        java.io.File(path).delete()
    }

    @Test
    fun testSaveFilePixelData() {
        val image = Image(2, 2)
        image[0, 0] = Color.red
        image[1, 0] = Color.green
        image[0, 1] = Color.blue
        image[1, 1] = Color.black

        val path = "test_pixels.png"

        // Save the image
        image.save(path)

        // Read the saved image
        val savedImage = javax.imageio.ImageIO.read(java.io.File(path))

        // Verify pixel data
        assertEquals(Color.red.rgb, savedImage.getRGB(0, 0), "Top-left saved pixel does not match.")
        assertEquals(Color.green.rgb, savedImage.getRGB(1, 0), "Top-right saved pixel does not match.")
        assertEquals(Color.blue.rgb, savedImage.getRGB(0, 1), "Bottom-left saved pixel does not match.")
        assertEquals(Color.black.rgb, savedImage.getRGB(1, 1), "Bottom-right saved pixel does not match.")

        // Clean up
        java.io.File(path).delete()
    }
}