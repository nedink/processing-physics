package art.nedink.processingphysics.cutec2;

/**
 * 2d vector
 */
public class C2v {

    public float x;
    public float y;

    public void add(C2v a) {
        x += a.x;
        y += a.y;
    }

    public void sub(C2v a) {
        x -= a.x;
        y -= a.y;
    }
}
