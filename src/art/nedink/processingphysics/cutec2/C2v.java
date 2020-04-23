package art.nedink.processingphysics.cutec2;

/**
 * 2d vector
 */
public class C2v {

    public float x;
    public float y;

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
}
