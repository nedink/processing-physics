package art.nedink.processingphysics.cutec2;

/**
 * contains all information necessary to resolve a collision, or in other words
 * this is the information needed to separate shapes that are colliding. Doing
 * the resolution step is *not* included in cute_c2.
 */
public class C2Manifold {

    public int count;
    public float[] depths = new float[2];
    public C2v[] contact_points = new C2v[2];

    /**
     * always points from shape A to shape B (first and second shapes passed into
     * any of the c2***to***Manifold functions)
     */
    public C2v n;
}
