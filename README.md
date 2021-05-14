# Asteroids
Remake of the ATARI Asteroids arcade game implemented in Java using JavaFX for the GUI library.

![alt text](https://i.imgur.com/xzGe4G2.png)

# Installation
Requirements:
You need the following installed:
- OpenJDK 15
- JavaFX 15

First `git clone https://github.com/OwaisSiddiqui/Asteroids`. Then run the following commands (with replacing the path name to paths on your machine where necessary):
```
cd Asteroids
export PATH_TO_FX=path/to/javafx-sdk-15.0.1/lib
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp src/asteroids:. ./src/asteroids/Main.java
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml ./src/asteroids/Main.java
```
# Objective
The objective of the game is to earn as many points possible by destroying all the asteroids in each level. Higher levels have more asteroids.

# Instructions to Play
Use the *space bar* to shoot bullets and the *up, right, and left* keys to move forward, right, and left respectively.

# Collision Detection
The collision detection in this version of the game is not AABB (Axis Aligned Bounding Box) like the original. It is a more acccurate collision detection called Separating Axis Theorem (SAT) collision detection.
