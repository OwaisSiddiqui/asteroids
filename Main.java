package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application
{
    Ship ship = new Ship();
//    Asteroid asteroidZero = new Asteroid(ship, this, 0);
//    Asteroid asteroidOne = new Asteroid(ship, this, 1);
//    Asteroid asteroidTwo = new Asteroid(ship, this, 2);
//    Asteroid asteroidThree = new Asteroid(ship, this, 3);
    Asteroid asteroidFour = new Asteroid(ship, this, 4);
    Asteroid[] asteroidArray = new Asteroid[5];
    Group group = new Group(//ship.getShipImage(), ship.getBulletZero().getBulletImage(),
            //ship.getBulletOne().getBulletImage(), ship.bulletTwo.getBulletImage(), ship.bulletThree.getBulletImage(),
            //ship.bulletFour.getBulletImage(),
//            asteroidZero.asteroidImage, asteroidOne.asteroidImage, asteroidTwo.asteroidImage,
//            asteroidThree.asteroidImage,
            asteroidFour.asteroidImage,
//            asteroidZero.text, asteroidOne.text, asteroidTwo.text,asteroidThree.text,
            asteroidFour.text,
//            asteroidZero.line, asteroidOne.line, asteroidTwo.line , asteroidThree.line,
            asteroidFour.line, asteroidFour.line1, asteroidFour.line2, asteroidFour.line3, asteroidFour.line4, asteroidFour.line5)
            ;
    Scene scene = new Scene(group, 700, 700);
//    EventHandler<KeyEvent> moveBulletEventFilterZero = ship.bulletZero.moveBullet();
//    EventHandler<KeyEvent> moveBulletEventFilterOne = ship.bulletOne.moveBullet();
//    EventHandler<KeyEvent> moveBulletEventFilterTwo = ship.bulletTwo.moveBullet();
//    EventHandler<KeyEvent> moveBulletEventFilterThree = ship.bulletThree.moveBullet();
//    EventHandler<KeyEvent> moveBulletEventFilterFour = ship.bulletFour.moveBullet();


//    public void areAsteroidsSeparate()
//    {
//        for (int i = 0; i < 5; i++)
//        {
//            for (int x = 0; x < 5; x++)
//            {
//                if (i == x)
//                {
//                    x+=1;
//                }
//                else if (asteroidArray[i].asteroidImage.getBoundsInParent().intersects(asteroidArray[x].asteroidImage.getBoundsInParent()))
//                {
////                    asteroidArray[i].setAsteroidPosition();
//                }
//            }
//        }
//    }

//    AnimationTimer setBulletToBeFired = new AnimationTimer()
//    {
//        @Override
//        public void handle(long now)
//        {
//            if (ship.latestBulletNotInTheBulletArray != null && ship.latestBulletNotInTheBulletArray.isBulletFired() && counter == 0)
//            {
//                scene.removeEventFilter(KeyEvent.KEY_PRESSED, getMoveBulletEventFilter(ship.latestBulletNotInTheBulletArray));
//                if (ship.bulletArray[0] != null)
//                {
//                    scene.addEventFilter(KeyEvent.KEY_PRESSED, getMoveBulletEventFilter(ship.bulletArray[0]));
//                }
//                else
//                {
//                    scene.addEventFilter(KeyEvent.KEY_PRESSED, getMoveBulletEventFilter(ship.tempEarliestBulletStorage[0]));
//                }
//                if (ship.bulletArray[0] != null)
//                {
//                    ship.tempBulletArrayStorage[1] = ship.bulletArray[0];
//                }
//                else
//                {
//                    ship.tempBulletArrayStorage[1] = ship.tempEarliestBulletStorage[0];
//                }
//                counter++;
//            }
//            else if (ship.latestBulletNotInTheBulletArray != null && ship.latestBulletNotInTheBulletArray == ship.tempBulletArrayStorage[1])
//            {
//                counter = 0;
//            }
//        }
//    };

//    public EventHandler<KeyEvent> getMoveBulletEventFilter(Bullet bullet)
//    {
//        if (bullet.getIdentificationNumber() == 0)
//        {
//            return moveBulletEventFilterZero;
//        }
//        else if (bullet.getIdentificationNumber() == 1)
//        {
//            return moveBulletEventFilterOne;
//        }
//        else if (bullet.getIdentificationNumber() == 2)
//        {
//            return moveBulletEventFilterTwo;
//        }
//        else if (bullet.getIdentificationNumber() == 3)
//        {
//            return moveBulletEventFilterThree;
//        }
//        else if (bullet.getIdentificationNumber() == 4)
//        {
//            return moveBulletEventFilterFour;
//        }
//        else
//        {
//            return null;
//        }
//    }

    @Override
    public void start(Stage primaryStage)
    {
//        asteroidArray[0] = asteroidZero;
//        asteroidArray[1] = asteroidOne;
//        asteroidArray[2] = asteroidTwo;
//        asteroidArray[3] = asteroidThree;
        asteroidArray[4] = asteroidFour;



//        areAsteroidsSeparate();
        scene.setFill(Color.TRANSPARENT);
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, ship.moveShip());
//        scene.addEventFilter(KeyEvent.KEY_RELEASED, ship.slowShip());
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, getMoveBulletEventFilter(ship.bulletArray[0]));
//        setBulletToBeFired.start();
        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}