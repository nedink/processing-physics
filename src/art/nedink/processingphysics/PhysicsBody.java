package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;
import art.nedink.processingphysics.cutec2.C2v;

public abstract class PhysicsBody {

    C2Circle perim;
    public C2v velocity = new C2v();
    public float restitution = 1f;
    public float mass = 0f;
}
