package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;

public class Asteroid extends PhysicsBody {

    public int size;

    public Asteroid(int s) {
        size = s;
        perim = new C2Circle();
        switch (s) {
            case 3:
                perim.r = 32;
                mass = 8f;
                break;
            case 2:
                perim.r = 16;
                mass = 4f;
                break;
            case 1:
                perim.r = 8;
                mass = 2f;
                break;
        }
    }
}
