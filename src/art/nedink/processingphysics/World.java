package art.nedink.processingphysics;

public class World {

    public static final float PHYSICS_SCALE = 0.1f;

    public static float physicsToDisplay(float value) {
        return value / PHYSICS_SCALE;
    }

    public static float displayToPhysics(float value) {
        return value * PHYSICS_SCALE;
    }
}
