package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;
import art.nedink.processingphysics.cutec2.C2v;

public class CircleBody {

    public C2v velocity = new C2v();
    public C2Circle circle;
    public float restitution = 1f;
    public final float mass = 1f;
}
