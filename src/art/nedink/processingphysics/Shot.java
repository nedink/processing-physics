package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;

public class Shot extends PhysicsBody {

    public Shot(float x, float y, float theta, float speed) {
        perim = new C2Circle();
        perim.p.x = x;
        perim.p.y = y;
        perim.r = 3;
        velocity.x = (float) Math.cos(theta) * speed;
        velocity.y = (float) Math.sin(theta) * speed;
        mass = 32f;
    }
}
