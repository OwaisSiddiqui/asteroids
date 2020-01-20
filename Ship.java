//package sample;
//
//import javafx.animation.AnimationTimer;
//import javafx.event.EventHandler;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polygon;
//
//public class Ship
//{
//    double acceleration = 17;
//    double rotation = 90;
//    double tempRotation = 0;
//    double currentAngle = 0;
//    double velocity = 0;
//    double velocityX = 0;
//    double velocityY = 0;
//    double directionX;
//    double directionY;
//    double counterOne = 0;
//    double counterTwo = 0;
//    double counterThree = 0;
//    double counterFour = 0;
//    double x = 0;
//    double y = 0;
//    double z = 0;
//    boolean right = false;
//    boolean left = false;
//    boolean up = false;
//    EventHandler<KeyEvent> key;
//    Polygon shipImage;
//    Bullet bulletZero = new Bullet(this);
//    Bullet bulletOne = new Bullet(this);
//    Bullet bulletTwo = new Bullet(this);
//    Bullet bulletThree = new Bullet(this);
//    Bullet bulletFour = new Bullet(this);
//    Bullet[] bulletArray = new Bullet[5];
//    Bullet[] tempBulletArrayStorage = new Bullet[2];
//    Bullet[] tempEarliestBulletStorage = new Bullet[5];
//    Bullet latestBulletNotInTheBulletArray;
//    Bullet[] bullets = new Bullet[5];
//
//        AnimationTimer moveShip = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//
//            }
//        };
//
//        AnimationTimer shipRotationTimer = new AnimationTimer()
//        {
//            @Override
//            public void handle(long now)
//            {
//                if (getRotation() == getX())
//                {
//                    setRotation(y);
//                    wrapShip();
//                }
//                else
//                {
//                    setRotation(getRotation() + getZ());
//                    setCurrentAngle(getCurrentAngle() - getZ());
//                    getShipImage().setRotate(getCurrentAngle());
//                    wrapShip();
//                }
//            }
//        };
//
//        AnimationTimer reiterateBulletsTimer = new AnimationTimer()
//        {
//            @Override
//            public void handle(long now)
//            {
//                if (bulletArray[0] != null && bulletArray[0].isBulletFired() && counterFour == 0)
//                {
//                    latestBulletNotInTheBulletArray = bulletArray[0];
//                    tempEarliestBulletStorage[lastSpotInTheBulletArray(tempEarliestBulletStorage, 4)] = bulletArray[0];
//                    bulletArray[0] = null;
//                    bulletArray[0] = bulletArray[1];
//                    bulletArray[1] = null;
//                    bulletArray[1] = bulletArray[2];
//                    bulletArray[2] = null;
//                    bulletArray[2] = bulletArray[3];
//                    bulletArray[3] = null;
//                    bulletArray[3] = bulletArray[4];
//                    bulletArray[4] = null;
//                    counterFour++;
//                }
//                else if (tempEarliestBulletStorage[0] != null && tempEarliestBulletStorage[0].isBulletStopped())
//                {
//                    bulletArray[lastSpotInTheBulletArray(bulletArray, 4)] = tempEarliestBulletStorage[0];
//                    tempEarliestBulletStorage[0] = tempEarliestBulletStorage[1];
//                    tempEarliestBulletStorage[1] = null;
//                    tempEarliestBulletStorage[1] = tempEarliestBulletStorage[2];
//                    tempEarliestBulletStorage[2] = null;
//                    tempEarliestBulletStorage[2] = tempEarliestBulletStorage[3];
//                    tempEarliestBulletStorage[3] = null;
//                    tempEarliestBulletStorage[3] = tempEarliestBulletStorage[4];
//                    tempEarliestBulletStorage[4] = null;
//                }
//            }
//        };
//
//        public boolean isBulletArrayFull()
//        {
//            int o = 0;
//            for (int h = 1; h >= 0; h--)
//            {
//                if (bulletArray[h] != null)
//                {
//                    o++;
//                }
//            }
//            return 2 == o;
//        }
//
//        Ship()
//        {
//            setShipImage(new Polygon());
//            Double[] array = new Double[]
//                    {
//                            0.0, 5.0,
//                            12.5, 15.0,
//                            0.0, -15.0,
//                            -12.5, 15.0,
//                            0.0, 5.0
//                    };
//            getShipImage().getPoints().addAll(array);
//            getShipImage().setFill(Color.TRANSPARENT);
//            getShipImage().setStroke(Color.BLACK);
//            getShipImage().setLayoutX(350);
//            getShipImage().setLayoutY(350);
//
//            bullets[0] = bulletZero;
//            bullets[1] = bulletOne;
//            bullets[2] = bulletTwo;
//            bullets[3] = bulletThree;
//            bullets[4] = bulletFour;
//
//            bulletArray[0] = bulletZero;
//            bulletArray[1] = bulletOne;
//            bulletArray[2] = bulletTwo;
//            bulletArray[3] = bulletThree;
//            bulletArray[4] = bulletFour;
//
//            bulletZero.setIdentificationNumber(0);
//            bulletOne.setIdentificationNumber(1);
//            bulletTwo.setIdentificationNumber(2);
//            bulletThree.setIdentificationNumber(3);
//            bulletFour.setIdentificationNumber(4);
//
//            speedX = 0;
//            speedY = 0;
//
//            reiterateBulletsTimer.start();
//        }
//
//        public int lastSpotInTheBulletArray(Bullet[] bulletArray, int number)
//        {
//            tempNumber = number;
//            while (number >= 0 && bulletArray[number] == null)
//            {
//                number--;
//            }
//            return number+1;
//        }
//
//        public void setAcceleration(double acceleration) {
//            this.acceleration = acceleration;
//        }
//
//        public double getAcceleration() {
//            return acceleration;
//        }
//
//        public void setRotation(double rotation) {
//            this.rotation = rotation;
//        }
//
//        public double getRotation() {
//            return rotation;
//        }
//
//        public void setTempRotation(double tempRotation) {
//            this.tempRotation = tempRotation;
//        }
//
//        public double getTempRotation() {
//            return tempRotation;
//        }
//
//        public void setCurrentAngle(double currentAngle) {
//            this.currentAngle = currentAngle;
//        }
//
//        public double getCurrentAngle() {
//            return currentAngle;
//        }
//
//        public double getVelocity() {
//            return velocity;
//        }
//
//        public void setVelocity(double velocity) {
//            this.velocity = velocity;
//        }
//
//        public double getVelocityX() {
//            return velocityX;
//        }
//
//        public void setVelocityX(double velocityX) {
//            this.velocityX = velocityX;
//        }
//
//        public double getVelocityY() {
//            return velocityY;
//        }
//
//        public void setVelocityY(double velocityY) {
//            this.velocityY = velocityY;
//        }
//
//        public double getCounterOne() {
//            return counterOne;
//        }
//
//        public void setCounterOne(double counterOne) {
//            this.counterOne = counterOne;
//        }
//
//        public double getCounterTwo() {
//            return counterTwo;
//        }
//
//        public void setCounterTwo(double counterTwo) {
//            this.counterTwo = counterTwo;
//        }
//
//        public double getCounterThree() {
//            return counterThree;
//        }
//
//        public void setCounterThree(double counterThree) {
//            this.counterThree = counterThree;
//        }
//
//        public double getX() {
//            return x;
//        }
//
//        public void setX(double x) {
//            this.x = x;
//        }
//
//        public double getY() {
//            return y;
//        }
//
//        public void setY(double y) {
//            this.y = y;
//        }
//
//        public double getZ() {
//            return z;
//        }
//
//        public void setZ(double z) {
//            this.z = z;
//        }
//
//        public boolean isRight() {
//            return right;
//        }
//
//        public void setRight(boolean right) {
//            this.right = right;
//        }
//
//        public boolean isLeft() {
//            return left;
//        }
//
//        public void setLeft(boolean left) {
//            this.left = left;
//        }
//
//        public boolean isUp() {
//            return up;
//        }
//
//        public void setUp(boolean up) {
//            this.up = up;
//        }
//
//        public EventHandler<KeyEvent> getKey() {
//            return key;
//        }
//
//        public void setKey(EventHandler<KeyEvent> key) {
//            this.key = key;
//        }
//
//        public Polygon getShipImage() {
//            return shipImage;
//        }
//
//        public void setShipImage(Polygon shipImage) {
//            this.shipImage = shipImage;
//        }
//
//        public AnimationTimer getCalculateVelocityX() {
//            return calculateVelocityX;
//        }
//
//        public void setCalculateVelocityX(AnimationTimer calculateVelocityX) {
//            this.calculateVelocityX = calculateVelocityX;
//        }
//
//        public AnimationTimer getCalculateVelocityY() {
//            return calculateVelocityY;
//        }
//
//        public void setCalculateVelocityY(AnimationTimer calculateVelocityY) {
//            this.calculateVelocityY = calculateVelocityY;
//        }
//
//        public AnimationTimer getShipAccelerationTimer() {
//            return shipAccelerationTimer;
//        }
//
//        public void setShipAccelerationTimer(AnimationTimer shipAccelerationTimer) {
//            this.shipAccelerationTimer = shipAccelerationTimer;
//        }
//
//        public AnimationTimer getShipRotationTimer() {
//            return shipRotationTimer;
//        }
//
//        public void setShipRotationTimer(AnimationTimer shipRotationTimer) {
//            this.shipRotationTimer = shipRotationTimer;
//        }
//
//        public Bullet getBulletZero() {
//            return bulletZero;
//        }
//
//        public void setBulletZero(Bullet bulletZero) {
//            this.bulletZero = bulletZero;
//        }
//
//        public Bullet getBulletOne() {
//            return bulletOne;
//        }
//
//        public void setBulletOne(Bullet bulletOne) {
//            this.bulletOne = bulletOne;
//        }
//
//        public Bullet[] getBulletArray() {
//            return bulletArray;
//        }
//
//        public void setBulletArray(Bullet[] bulletArray) {
//            this.bulletArray = bulletArray;
//        }
//
//        public class calculateVelocityX implements Runnable
//        {
//            public void run()
//            {
//                calculateVelocityX.start();
//            }
//        }
//
//        public class calculateVelocityY implements Runnable
//        {
//            public void run()
//            {
//                calculateVelocityY.start();
//            }
//        }
//
//        public class accelerateShip implements Runnable
//        {
//            public void run()
//            {
//                shipAccelerationTimer.start();
//            }
//        }
//
////    public class calculateSpeedX implements Runnable
////    {
////        public void run()
////        {
////            calculateSpeedX.start();
////        }
////    }
////
////    public class calculateSpeedY implements Runnable
////    {
////        public void run()
////        {
////            calculateSpeedY.start();
////        }
////    }
//
//        public void MoveShip()
//        {
//            (new Thread(new calculateVelocityX())).start();
//            (new Thread(new calculateVelocityY())).start();
//            (new Thread(new accelerateShip())).start();
////        (new Thread(new calculateSpeedX())).start();
////        (new Thread(new calculateSpeedY())).start();
//        }
//
//        public void Rotate(int direction)
//        {
//            if (direction == 0)
//            {
//                setX(0);
//                setY(360);
//                setZ(-5);
//                if (shipImage.getRotate() > 0 && shipImage.getRotate() < 90) {
//                    this.directionX = 1;
//                }
//                getShipRotationTimer().start();
//
//            }
//            if (direction == 1)
//            {
//                setX(360);
//                setY(0);
//                setZ(5);
//                getShipRotationTimer().start();
//            }
//        }
//
//        public void wrapShip()
//        {
//            if (getShipImage().getLayoutX() > 700)
//            {
//                getShipImage().setLayoutX(1);
//            }
//            else if (getShipImage().getLayoutY() > 700)
//            {
//                getShipImage().setLayoutY(1);
//            }
//            else if (getShipImage().getLayoutX() < 0)
//            {
//                getShipImage().setLayoutX(699);
//            }
//            else if (getShipImage().getLayoutY() < 0)
//            {
//                getShipImage().setLayoutY(699);
//            }
//        }
//
//        public EventHandler<KeyEvent> moveShip()
//        {
//            return key = (KeyEvent keyEvent) ->
//            {
//                if (keyEvent.getCode() == KeyCode.RIGHT) {
//                    Rotate(0);
//                    setRight(true);
//                } else if (keyEvent.getCode() == KeyCode.LEFT) {
//                    Rotate(1);
//                    setLeft(true);
//                }
//                if (keyEvent.getCode() == KeyCode.UP) {
//                    if (getCounterThree() == 0) {
//                        MoveShip();
//                        setUp(true);
//                        setCounterThree(getCounterThree() + 1);
//                    } else if (getAcceleration() == -4) {
//                        setAcceleration(17);
//                        setCounterOne(0);
//                        setCounterTwo(0);
//                    }
//                } else if (keyEvent.getCode() == KeyCode.RIGHT && left) {
//                    Rotate(0);
//                    setRight(true);
//                } else if (keyEvent.getCode() == KeyCode.LEFT && right) {
//                    Rotate(1);
//                    setLeft(true);
//                }
//            };
//        }
//
//        public EventHandler<KeyEvent> slowShip()
//        {
//            return key = (KeyEvent keyEvent) ->
//            {
//                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT)
//                {
//                    System.out.println("Rotation: "+getRotation());
//                    setRight(false);
//                    setLeft(false);
//                    getShipRotationTimer().stop();
//                }
//                if (keyEvent.getCode() == KeyCode.UP)
//                {
//                    if (getCounterTwo() == 0)
//                    {
//                        setUp(false);
//                        setAcceleration(-4);
//                        if (getCounterOne() == 0)
//                        {
//                            setTempRotation(getRotation());
//                            setCounterOne(getCounterOne() + 1);
//                        }
//                        setCounterTwo(getCounterTwo() + 1);
//                    }
//                }
//            };
//        }
//}
