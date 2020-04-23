package art.nedink.processingphysics;

import art.nedink.processingphysics.cutec2.C2Circle;
import art.nedink.processingphysics.cutec2.C2Manifold;
import art.nedink.processingphysics.cutec2.C2v;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static art.nedink.processingphysics.cutec2.CuteC2.c2CircletoCircle;
import static art.nedink.processingphysics.cutec2.CuteC2.c2CircletoCircleManifold;
import static art.nedink.processingphysics.cutec2.CuteC2.c2Random;
import static art.nedink.processingphysics.cutec2.CuteC2.c2V;
import static art.nedink.processingphysics.cutec2.CuteC2.resolveCollision;

public class Game extends PApplet {

    Player player;
    Shot shot;
    List<Asteroid> asteroids = new ArrayList<>();

    public final Map<String, Boolean> keysPressed = new HashMap<String, Boolean>() {{
        put("up", false);
        put("down", false);
        put("left", false);
        put("right", false);
    }};

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

        player = new Player();
        player.perim = new C2Circle();
        player.perim.p = new C2v();
        player.perim.p.x = width / 2;
        player.perim.p.y = width / 2;
        player.perim.r = 16f;

        for (int i = 0; i < 3; i++) {
            Asteroid a = new Asteroid(3);
            a.perim.p.x = random(width);
            a.perim.p.y = random(height);
            a.velocity = c2Random();
            asteroids.add(a);
        }
    }

    @Override
    public void draw() {

        // collision?
        fill(255);
//        if (c2CircletoCircle(body1.circle, body2.circle)) {
//            C2Manifold m = c2CircletoCircleManifold(body1.circle, body2.circle);
//            resolveCollision(body1, body2, m);
//        }

        // draw
        background(0);

        // draw circles
        ellipseMode(CENTER);
        strokeWeight(1);
        stroke(255);
        noFill();
        asteroids.forEach(a -> circle(a.perim.p.x, a.perim.p.y, a.perim.r * 2));
        circle(player.perim.p.x, player.perim.p.y, player.perim.r * 2);
        if (shot != null) {
            circle(shot.perim.p.x, shot.perim.p.y, shot.perim.r * 2);
        }

        // update
//        body1.circle.p.x = mouseX;
//        body1.circle.p.y = mouseY;
        asteroids.forEach(a -> {
            a.perim.p.add(a.velocity);
            wrap(a);
        });

        player.perim.p.add(player.velocity);
        wrap(player);

        if (shot != null) {
            shot.perim.p.add(shot.velocity);

            // check for asteroids hit by shot
            for (Asteroid a : asteroids) {
                if (c2CircletoCircle(shot.perim, a.perim)) {
                    C2Manifold m = c2CircletoCircleManifold(shot.perim, a.perim);
                    resolveCollision(shot, a, m);

                    if (a.size > 1) {
                        for (int i = 0; i < Math.random() * 3 + 1; i++) {
                            Asteroid a1 = new Asteroid(a.size - 1);
                            a1.perim.p.x = a.perim.p.x;
                            a1.perim.p.y = a.perim.p.y;
                            a1.velocity = c2Random();
                            asteroids.add(a1);
                        }
                    }
                    asteroids.remove(a);
                    shot = null;
                    break;
                }
            }
        }

        if (keysPressed.get("left")) {
            player.velocity.add(c2V(-0.1f, 0));
        }
        if (keysPressed.get("right")) {
            player.velocity.add(c2V(0.1f, 0));
        }
        if (keysPressed.get("up")) {
            player.velocity.add(c2V(0, -0.1f));
        }
        if (keysPressed.get("down")) {
            player.velocity.add(c2V(0, 0.1f));
        }

        if (shot != null) {
            if (shot.perim.p.x < 25 || shot.perim.p.x > width + 25 || shot.perim.p.y < -25 || shot.perim.p.y > height + 25) {
                shot = null;
            }
        }

    }

    public void wrap(PhysicsBody b) {
        if (b.perim.p.x < -25) {
            b.perim.p.x += width + 50;
        }
        if (b.perim.p.x > width + 25) {
            b.perim.p.x -= width + 50;
        }
        if (b.perim.p.y < -25) {
            b.perim.p.y += height + 50;
        }
        if (b.perim.p.y > width + 25) {
            b.perim.p.y -= height + 50;
        }
    }

    @Override
    public void mousePressed() {
        if (shot != null)
            return;

        shot = new Shot(player.perim.p.x, player.perim.p.y);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKey()) {
            case 'a':
                keysPressed.put("left", true);
                break;
            case 'd':
                keysPressed.put("right", true);
                break;
            case 'w':
                keysPressed.put("up", true);
                break;
            case 's':
                keysPressed.put("down", true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKey()) {
            case 'a':
                keysPressed.put("left", false);
                break;
            case 'd':
                keysPressed.put("right", false);
                break;
            case 'w':
                keysPressed.put("up", false);
                break;
            case 's':
                keysPressed.put("down", false);
                break;
        }
    }
}
