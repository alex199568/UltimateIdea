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

    private var shearingXy = 0.0
    private var shearingXz = 0.0
    private var shearingYx = 0.0
    private var shearingYz = 0.0
    private var shearingZx = 0.0
    private var shearingZy = 0.0

    fun translate(x: Number, y: Number, z: Number): AffineBuilder {
        this.x += x.toDouble()
        this.y += y.toDouble()
        this.z += z.toDouble()
        return this
    }

    fun moveX(x: Number): AffineBuilder {
        this.x += x.toDouble()
        return this
    }

    fun moveLeft(amount: Number): AffineBuilder {
        this.x -= amount.toDouble()
        return this
    }

    fun moveRight(amount: Number): AffineBuilder {
        this.x += amount.toDouble()
        return this
    }

    fun moveUp(amount: Number): AffineBuilder {
        this.y += amount.toDouble()
        return this
    }

    fun moveDown(amount: Number): AffineBuilder {
        this.y -= amount.toDouble()
        return this
    }

    fun moveForward(amount: Number): AffineBuilder {
        this.z += amount.toDouble()
        return this
    }

    fun moveBackward(amount: Number): AffineBuilder {
        this.z -= amount.toDouble()
        return this
    }

    fun moveY(y: Number): AffineBuilder {
        this.y += y.toDouble()
        return this
    }

    fun moveZ(z: Number): AffineBuilder {
        this.z += z.toDouble()
        return this
    }

    fun rotateX(rads: Number): AffineBuilder {
        rx += rads.toDouble()
        return this
    }

    fun rotateXd(degrees: Number): AffineBuilder {
        rx += Math.toRadians(degrees.toDouble())
        return this
    }

    fun rotateY(rads: Number): AffineBuilder {
        ry += rads.toDouble()
        return this
    }

    fun rotateYd(degrees: Number): AffineBuilder {
        ry += Math.toRadians(degrees.toDouble())
        return this
    }

    fun rotateZ(rads: Number): AffineBuilder {
        rz += rads.toDouble()
        return this
    }

    fun rotateZd(degrees: Number): AffineBuilder {
        rz += Math.toRadians(degrees.toDouble())
        return this
    }

    fun scale(x: Number, y: Number, z: Number): AffineBuilder {
        sx *= x.toDouble()
        sy *= y.toDouble()
        sz *= z.toDouble()
        return this
    }

    fun grow(x: Number, y: Number, z: Number): AffineBuilder {
        return scale(x, y, z)
    }

    fun shrink(x: Number, y: Number, z: Number): AffineBuilder {
        val dx = x.toDouble()
        require(dx != 0.0)
        val dy = y.toDouble()
        require(dy != 0.0)
        val dz = z.toDouble()
        require(dz != 0.0)
        sx /= dx
        sy /= dy
        sz /= dz
        return this
    }

    fun scaleX(x: Number): AffineBuilder {
        sx *= x.toDouble()
        return this
    }

    fun growX(x: Number): AffineBuilder {
        sx *= x.toDouble()
        return this
    }

    fun shrinkX(x: Number): AffineBuilder {
        val xd = x.toDouble()
        require(xd != 0.0)
        sx /= xd
        return this
    }

    fun scaleY(y: Number): AffineBuilder {
        sy *= y.toDouble()
        return this
    }

    fun growY(y: Number): AffineBuilder {
        sy *= y.toDouble()
        return this
    }

    fun shrinkY(y: Number): AffineBuilder {
        val yd = y.toDouble()
        require(yd != 0.0)
        sy /= yd
        return this
    }

    fun scaleZ(z: Number): AffineBuilder {
        sz *= z.toDouble()
        return this
    }

    fun growZ(z: Number): AffineBuilder {
        sz *= z.toDouble()
        return this
    }

    fun shrinkZ(z: Number): AffineBuilder {
        val zd = z.toDouble()
        require(zd != 0.0)
        sz /= zd
        return this
    }

    fun shear(
        sx: Number, sy: Number,
        yx: Number, yz: Number,
        zx: Number, zy: Number
    ): AffineBuilder {
        shearingXy = sx.toDouble()
        shearingXz = sy.toDouble()
        shearingYx = yx.toDouble()
        shearingYz = yz.toDouble()
        shearingZx = zx.toDouble()
        shearingZy = zy.toDouble()
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

        if (
            shearingXy != 0.0 || shearingXz != 0.0 ||
            shearingYx != 0.0 || shearingYz != 0.0 ||
            shearingZx != 0.0 || shearingZy != 0.0
        ) {
            val s = Affine.shearing(
                shearingXy, shearingXz,
                shearingYx, shearingYz,
                shearingZx, shearingZy
            )
            if (modified) {
                result *= s
            } else {
                modified = true
                result = s
            }
        }

        if (sx != 1.0 || sy != 1.0 || sz != 1.0) {
            val s = Affine.scale(sx, sy, sz)
            if (modified) {
                result *= s
            } else {
                result = s
                modified = true
            }
        }

        return result
    }
}
