package art.nedink.processingphysics.cutec2;

/**
 * 2d vector
 */
public class C2v {

    public float x;
    public float y;

    public float mag() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public C2v add(C2v a) {
        x += a.x;
        y += a.y;
        return this;
    }

    public C2v sub(C2v a) {
        x -= a.x;
        y -= a.y;
        return this;
    }

    public C2v mult(float a) {
        x *= a;
        y *= a;
        return this;
    }

    public C2v div(float a) {
        x /= a;
        y /= a;
        return this;
    }

    public C2v normalize() {
        float m = this.mag();
        if (m != 0.0F && m != 1.0F) {
            this.div(m);
        }
        return this;
    }

    public C2v setMag(float len) {
        this.normalize();
        this.mult(len);
        return this;
    }

    public void zeroOut() {
        x = y = 0;
    }
}
