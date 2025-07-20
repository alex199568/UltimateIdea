package org.example.app

import org.example.app.linalg.transform

private fun renderSimpleImage() {
    val image = Image(128, 96)

    for (y in 20 until 32) {
        for (x in 30 until 100) {
            image[x, y] = Color.red
        }
    }

    image.save("renders/image.png")
}

fun main() {
    println("Ultimate Idea")

    val tr = transform {
        moveLeft(2)
        moveUp(3)
        rotateXd(45)
        growX(2)
        growY(3)
        shrinkZ(1.5)
    }
    println(tr)
}
