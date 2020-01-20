//package sample;
//
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//public class Main extends Application
//{
//    Ship ship = new Ship();
//    Group group = new Group(ship.getShipImage());
//    Scene scene = new Scene(group, 700, 700);
//
//    @Override
//    public void start(Stage primaryStage)
//    {
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, ship.moveShip());
//        scene.addEventFilter(KeyEvent.KEY_RELEASED, ship.slowShip());
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.setTitle("Asteroids");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args)
//    {
//        launch(args);
//    }
//}
