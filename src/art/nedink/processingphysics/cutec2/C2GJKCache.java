package art.nedink.processingphysics.cutec2;

/**
 * This struct is only for advanced usage of the c2GJK function. See comments inside of the
 * c2GJK function for more details.
 */
public class C2GJKCache {

    float metric;
    int count;
    int[] iA = new int[3];
    int[] iB = new int[3];
    float div;

}
