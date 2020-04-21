package art.nedink.physics;

import art.nedink.geom.Circle;
import art.nedink.geom.Vec2;

public class Collision {

    static float Distance(Vec2 a, Vec2 b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static boolean CirclevsCircleUnoptimized(Circle a, Circle b) {
        float r = a.radius + b.radius;
        return r < Distance(a.position, b.position);
    }

    static boolean CirclevsCircleOptimized(Circle a, Circle b) {
        float r = a.radius + b.radius;
        r *= r;
        return r < Math.pow(a.x() + b.x(), 2) + Math.pow(a.y() + b.y(), 2);
    }

}