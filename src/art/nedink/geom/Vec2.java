package art.nedink.geom;

public class Vec2 {

    public float x;
    public float y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Vec2 VecAB(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static float Dot(Vec2 a, Vec2 b) {
        return a.x * b.x + a.y * b.y;
    }
}
