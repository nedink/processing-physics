package art.nedink.processingphysics.cutec2;

/**
 * 2d transformation "x"
 * These are used especially for c2Poly when a c2Poly is passed to a function.
 * Since polygons are prime for "instancing" a c2x transform can be used to
 * transform a polygon from local space to world space. In functions that take
 * a c2x pointer (like c2PolytoPoly), these pointers can be NULL, which represents
 * an identity transformation and assumes the verts inside of c2Poly are already
 * in world space.
 */
public class C2x {

    C2v p;
    C2r r;
}
