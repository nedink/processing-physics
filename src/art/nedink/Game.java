package art.nedink;

import processing.core.PApplet;

public class Game extends PApplet {

    public static void main(String[] args) {
        PApplet.main("art.nedink.Game");
    }

    @Override
    public void settings() {
        size(512, 512);
        noSmooth();
    }

    public void setup() {
        frameRate(60);
    }

    @Override
    public void draw() {
        background(0);
    }
}
