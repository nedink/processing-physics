package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;

public class Shot extends PhysicsBody {

    public Shot(float x, float y) {
        perim = new C2Circle();
        perim.p.x = x;
        perim.p.y = y;
        perim.r = 3;
        velocity.x = 2;
        velocity.y = 2;
        mass = 32f;
    }
}
