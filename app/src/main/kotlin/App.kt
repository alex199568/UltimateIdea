package org.example.app

fun main() {
    println("Ultimate Idea")

    val image = Image(128, 96)

    for (y in 20 until 32) {
        for (x in 30 until 100) {
            image[x, y] = Color.red
        }
    }

    image.save("renders/image.png")
}
