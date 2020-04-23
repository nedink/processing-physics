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

import static art.nedink.processingphysics.cutec2.CuteC2.*;

public class Game extends PApplet {

    HealthBar healthBar;
    Player player;
    Shot shot;
    List<Asteroid> asteroids;
    boolean gameOver;
    float hurtAnim;

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
        fullScreen();
//        size(512, 512);
//        noSmooth();
    }

    public void setup() {
        newGame();

        frameRate(60);
        background(0);
    }

    public void newGame() {
        player = new Player();
        player.perim = new C2Circle();
        player.perim.p = new C2v();
        player.perim.p.x = width / 2;
        player.perim.p.y = height / 2;
        player.perim.r = 16f;
        player.mass = 1f;
//        player.restitution = 0f;

        asteroids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Asteroid a = new Asteroid(3);
            a.perim.p.x = random(width);
            a.perim.p.y = random(height);
            if (a.perim.p.x > width / 4 && a.perim.p.x < 3 * width / 4 &&
                a.perim.p.y > height / 4 && a.perim.p.y < 3 * height / 4) {
                a.perim.p.x += 100 * Math.round(Math.random());
                a.perim.p.y += 100 * Math.round(Math.random());
            }
            a.velocity = c2Random().setMag((float) (Math.random() * 4 + 1));
            asteroids.add(a);
        }

        healthBar = new HealthBar();
        hurtAnim = 0;
        gameOver = false;
    }

    @Override
    public void draw() {

        physicsUpdate();

        // collision?
        fill(255);
//        if (c2CircletoCircle(body1.circle, body2.circle)) {
//            C2Manifold m = c2CircletoCircleManifold(body1.circle, body2.circle);
//            resolveCollision(body1, body2, m);
//        }

        // draw background
        background(0);

        // draw health bar
        noStroke();
        fill(255 * (100 - healthBar.health), healthBar.health * (255f / 100f), 0);
        if (healthBar.health > 0) {
            rect(width / 2 - 100, 16, healthBar.health * 2, 16);

            fill(80);
            rect(healthBar.health * 2 + width / 2 - 100, 16, (100 - healthBar.health) * 2 , 16);
        }

        // draw circles
        ellipseMode(CENTER);
//        strokeWeight(1);
//        stroke(255);
//        noFill();
        fill(255);
        asteroids.forEach(a -> circle(a.perim.p.x, a.perim.p.y, a.perim.r * 2));
//        stroke(0, 255, 0);
        fill(255 * (100 - healthBar.health), healthBar.health * (255f / 100f), 0);

        if (!gameOver) {
            circle(player.perim.p.x, player.perim.p.y, player.perim.r * 2);
        }

        if (shot != null) {
            circle(shot.perim.p.x, shot.perim.p.y, shot.perim.r * 2);
        }

        if (hurtAnim > 0) {
            fill(255, 0, 0, hurtAnim * 0.1f * 255);
            rect(0, 0, width, height);
            hurtAnim *= 0.8;
            if (hurtAnim < 0.01) {
                hurtAnim = 0;
            }
        }

        if (gameOver) {
            textAlign(CENTER, CENTER);
            textSize(24);
            fill(255, 0, 0);
            text("GAME OVER", width / 2, height / 2);
        }
    }

    public void physicsUpdate() {
        // update
//        body1.circle.p.x = mouseX;
//        body1.circle.p.y = mouseY;

        // for each asteroid...
        for (Asteroid a1 : asteroids) {
            // other asteroids
            for (Asteroid a2 : asteroids) {
                if (a1 == a2) continue;
                if (c2CircletoCircle(a1.perim, a2.perim)) {
                    resolveCollision(a1, a2, c2CircletoCircleManifold(a1.perim, a2.perim));
                }
            }
            // player
            if (!gameOver) {
                if (c2CircletoCircle(a1.perim, player.perim)) {
                    C2v rv = c2Sub(a1.velocity, player.velocity);
                    resolveCollision(a1, player, c2CircletoCircleManifold(a1.perim, player.perim));
                    healthBar.health -= rv.mag();
                    hurtAnim = rv.mag();
                }
            }
        }

        asteroids.forEach(a -> {
            a.perim.p.add(a.velocity);
            wrap(a);
        });

        player.velocity.setMag(Math.min(player.velocity.mag(), 4f));
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
                            a1.velocity = c2Random().setMag((float) (Math.random() * 4 + 1));
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

        if (healthBar.health <= 0) {
            gameOver = true;
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
        if (b.perim.p.y > height + 25) {
            b.perim.p.y -= height + 50;
        }
    }

    @Override
    public void mousePressed() {
        if (shot != null || gameOver)
            return;

        shot = new Shot(player.perim.p.x, player.perim.p.y, (float) Math.atan2(mouseY - player.perim.p.y, mouseX - player.perim.p.x), 8);
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
            case 'r':
                newGame();;
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
