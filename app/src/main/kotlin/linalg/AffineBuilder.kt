package org.example.app.linalg

class AffineBuilder {

    private var x = 0.0
    private var y = 0.0
    private var z = 0.0

    private var rx = 0.0
    private var ry = 0.0
    private var rz = 0.0

    private var sx = 1.0
    private var sy = 1.0
    private var sz = 1.0

    fun translate(x: Number, y: Number, z: Number): AffineBuilder {
        this.x += x.toDouble()
        this.y += y.toDouble()
        this.z += z.toDouble()
        return this
    }

    fun rotateX(rads: Number): AffineBuilder {
        rx += rads.toDouble()
        return this
    }

    fun rotateY(rads: Number): AffineBuilder {
        ry += rads.toDouble()
        return this
    }

    fun rotateZ(rads: Number): AffineBuilder {
        rz += rads.toDouble()
        return this
    }

    fun scale(x: Number, y: Number, z: Number): AffineBuilder {
        sx *= x.toDouble()
        sy *= y.toDouble()
        sz *= z.toDouble()
        return this
    }

    fun build(): Affine {
        var result = Affine.identity
        var modified = false

        if (x != 0.0 || y != 0.0 || z != 0.0) {
            val tr = Affine.translate(x, y, z)
            result = tr
            modified = true
        }

        if (rx != 0.0) {
            val r = Affine.rotateX(rx)
            if (modified) {
                result *= r
            } else {
                result = r
                modified = true
            }
        }

        if (ry != 0.0) {
            val r = Affine.rotateY(ry)
            if (modified) {
                result *= r
            } else {
                result = r
                modified = true
            }
        }

        if (rz != 0.0) {
            val r = Affine.rotateZ(rz)
            if (modified) {
                result *= r
            } else {
                result = r
                modified = true
            }
        }

        if (sx != 1.0 || sy != 1.0 || sz != 1.0) {
            val s = Affine.scale(sx, sy, sz)
            if (modified) {
                result *= s
            } else {
                result = s
            }
        }

        return result
    }
}
