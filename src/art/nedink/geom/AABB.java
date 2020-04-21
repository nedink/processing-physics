package art.nedink.geom;

import processing.core.PVector;

public class AABB {

    public Vec2 min;
    public Vec2 max;

    public boolean AABBvsAABB(AABB a, AABB b) {
        // Exit with no intersection if found separated along an axis
        if (a.max.x < b.min.x || a.min.x > b.max.x) return false;
        if (a.max.y < b.min.y || a.min.y > b.max.y) return false;
        // No separating axis found, therefor there is at least one overlapping axis
        return true;
    }
}
