package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Launcher {

    private final Ship ship;
    protected ArrayList<Bullet> bullets;
    private final int numberOfBullets;
    private int numberOfBulletsFired = 0;
    private boolean isLaunching = false;
    private boolean isSpacePressed = false;
    private long lastUpdate;

    Launcher(Ship ship, ArrayList<Bullet> bullets) {
        this.ship = ship;
        this.bullets = bullets;
        numberOfBullets = bullets.size();
        final AnimationTimer detectSpaceHold = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long keyHold = 700;
                if (isSpacePressed) {
                    if (System.currentTimeMillis() - lastUpdate >= keyHold) {
                        isLaunching = true;
                    }
                } else {
                    isLaunching = false;
                }
            }
        };
        detectSpaceHold.start();
        AnimationTimer updatePosition = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Bullet bullet : bullets) {
                    bullet.setStartingX(ship.getDecomposedPolygonsPositionPoints().get(0)[0].getX());
                    bullet.setStartingY(ship.getDecomposedPolygonsPositionPoints().get(0)[0].getY());
                }
            }
        };
        updatePosition.start();
        AnimationTimer start = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long l) {
                long launchDelay = 100_000_000;
                if (l - lastUpdate >= launchDelay) {
                    if (isLaunching) {
                        launch();
                    }
                    lastUpdate = l;
                }
            }
        };
        start.start();
    }

    private void launch() {
        if (numberOfBulletsFired >= numberOfBullets) { numberOfBulletsFired = 0; }
        if (bullets.get(numberOfBulletsFired).isReadyToStart()) {
            bullets.get(numberOfBulletsFired).start(ship.getDirectionX(), ship.getDirectionY());
            numberOfBulletsFired++;
        }
    }

    public ArrayList<Bullet> getBullets() { return bullets; }

    EventHandler<KeyEvent> detectLaunch(EventType<KeyEvent> keyEventPR) {
        if (keyEventPR == KeyEvent.KEY_PRESSED) {
            return keyEvent -> {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    launch();
                    if (!isSpacePressed) {
                        lastUpdate = System.currentTimeMillis();
                    }
                    isSpacePressed = true;
                }
            };
        }
        else if (keyEventPR == KeyEvent.KEY_RELEASED) {
            return keyEvent -> {
                if (keyEvent.getCode() == KeyCode.SPACE) { isSpacePressed = false; }
            };
        }
        else {
            return keyEvent -> {};
        }
    }

}
