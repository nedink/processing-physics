package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;
import art.nedink.processingphysics.cutec2.C2Manifold;
import art.nedink.processingphysics.cutec2.C2v;
import processing.core.PApplet;

import static art.nedink.processingphysics.cutec2.CuteC2.c2CircletoCircle;
import static art.nedink.processingphysics.cutec2.CuteC2.c2CircletoCircleManifold;
import static art.nedink.processingphysics.cutec2.CuteC2.resolveCollision;

public class Game extends PApplet {

    CircleBody body1;
    CircleBody body2;

    public static void main(String[] args) {
        PApplet.main("art.nedink.processingphysics.Game");
    }

    @Override
    public void settings() {
        size(512, 512);
        noSmooth();
    }

    public void setup() {
        frameRate(60);

        body1 = new CircleBody();
        C2Circle c1 = new C2Circle();
        c1.p = new C2v();
        c1.p.x = 50;
        c1.p.y = 90;
        c1.r = 20;
        body1.circle = c1;
        body1.velocity.x = 0.5f;

        body2 = new CircleBody();
        C2Circle c2 = new C2Circle();
        c2.p = new C2v();
        c2.p.x = 200;
        c2.p.y = 70;
        c2.r = 25;
        body2.circle = c2;
    }

    @Override
    public void draw() {

        // collision?
        fill(255);
        if (c2CircletoCircle(body1.circle, body2.circle)) {
            C2Manifold m = c2CircletoCircleManifold(body1.circle, body2.circle);
            resolveCollision(body1, body2, m);
        }

        // draw
        background(0);

        // draw circles
        ellipseMode(CENTER);
        strokeWeight(1);
        stroke(255);
        noFill();
        circle(body1.circle.p.x, body1.circle.p.y, body1.circle.r * 2);
        circle(body2.circle.p.x, body2.circle.p.y, body2.circle.r * 2);

        // update
//        body1.circle.p.x = mouseX;
//        body1.circle.p.y = mouseY;
        body1.circle.p.add(body1.velocity);
        body2.circle.p.add(body2.velocity);
    }
}
