package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;

public class Bullet
{
    Circle bulletImage;
    double counter = 0;
    EventHandler<KeyEvent> key;
    Ship ship;
    double counterTwo = 0;
    double tempRotation = 0;
    boolean bulletFired = false;
    int identificationNumber = 0;
    boolean bulletStopped = false;

    AnimationTimer bulletAccelerationTimer = new AnimationTimer()
    {
        @Override
        public void handle(long now)
        {
            if (getCounterTwo() == 0)
            {
                getBulletImage().setFill(Color.WHITE);
                setBulletFired(true);
                ship.counterFour = 0;
                setBulletPosition();
                setCounterTwo(getCounterTwo() + 1);
                tempRotation = ship.rotation;
            }
            getBulletImage().setLayoutX(getBulletImage().getLayoutX() + (Math.cos(Math.toRadians(tempRotation))*10));
            getBulletImage().setLayoutY(getBulletImage().getLayoutY() - (Math.sin(Math.toRadians(tempRotation))*10));
            wrapBullet();
            setCounter(getCounter() + 1);
            if (getCounter() == 50)
            {
                System.out.println("Stop everything!");
                getBulletImage().setFill(Color.TRANSPARENT);
                setBulletStopped(true);
                getBulletAccelerationTimer().stop();
                setBulletFired(false);
                setBulletPosition();
                getBulletAccelerationTimer().stop();
                setCounter(0);
                setCounterTwo(0);
            }
        }
    };

    AnimationTimer bulletPositionSet = new AnimationTimer()
    {
        @Override
        public void handle(long now)
        {
            getBulletImage().setLayoutX(ship.shipImage.getLayoutX());
            getBulletImage().setLayoutY(ship.shipImage.getLayoutY());
        }
    };

    Bullet(Ship ship)
    {
        this.ship = ship;
        setBulletImage(new Circle());
        getBulletImage().setRadius(4);
    }

    public void setBulletPosition()
    {
        getBulletImage().setLayoutX(ship.getShipImage().getLayoutX());
        getBulletImage().setLayoutY(ship.getShipImage().getLayoutY());
    }

    public Circle getBulletImage()
    {
        return bulletImage;
    }

    public void setBulletImage(Circle bulletImage)
    {
        this.bulletImage = bulletImage;
    }

    public double getCounter()
    {
        return counter;
    }

    public void setCounter(double counter)
    {
        this.counter = counter;
    }

    public double getCounterTwo()
    {
        return counterTwo;
    }

    public void setCounterTwo(double counterTwo)
    {
        this.counterTwo = counterTwo;
    }

    public EventHandler<KeyEvent> getKey()
    {
        return key;
    }

    public void setKey(EventHandler<KeyEvent> key)
    {
        this.key = key;
    }

    public AnimationTimer getBulletAccelerationTimer()
    {
        return bulletAccelerationTimer;
    }

    public void setBulletAccelerationTimer(AnimationTimer bulletAccelerationTimer)
    {
        this.bulletAccelerationTimer = bulletAccelerationTimer;
    }

    public AnimationTimer getBulletPositionSet()
    {
        return bulletPositionSet;
    }

    public void setBulletPositionSet(AnimationTimer bulletPosition)
    {
        this.bulletPositionSet = bulletPosition;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void MoveBullet()
    {
        getBulletAccelerationTimer().start();
    }

    public boolean isBulletFired() {
        return bulletFired;
    }

    public void setBulletFired(boolean bulletFired) {
        this.bulletFired = bulletFired;
    }

    public boolean isBulletStopped() {
        return bulletStopped;
    }

    public void setBulletStopped(boolean bulletStopped) {
        this.bulletStopped = bulletStopped;
    }

    public void stopBullet()
    {
        setCounter(50);
    }

    public void wrapBullet()
    {
        if (getBulletImage().getLayoutX() > 700)
        {
            getBulletImage().setLayoutX(1);
        }
        if (getBulletImage().getLayoutY() > 700)
        {
            getBulletImage().setLayoutY(1);
        }
        if (getBulletImage().getLayoutX() < 0)
        {
            getBulletImage().setLayoutX(699);
        }
        if (getBulletImage().getLayoutY() < 0)
        {
            getBulletImage().setLayoutY(699);
        }
    }

    public EventHandler<KeyEvent> moveBullet()
    {
        return key = (KeyEvent keyEvent) ->
        {
            if (keyEvent.getCode() == KeyCode.SPACE)
            {
                if (getCounter() == 0)
                {
                    MoveBullet();
                    setCounter(getCounter() + 1);
                }
            }
        };
    }
}
