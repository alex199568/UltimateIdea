package org.example.app.linalg

fun transform(transformer: AffineBuilder.() -> Unit): Affine {
    val builder = AffineBuilder()
    transformer(builder)
    return builder.build()
}
