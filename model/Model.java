package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Model {
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader;

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (GameObject wall: getGameObjects().getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                for (Box item : gameObjects.getBoxes()) {
                    if (!box.equals(item)) {
                        if (box.isCollision(item, direction)) {
                            return true;
                        }
                    }
                    if (checkWallCollision(box, direction)) {
                        return true;
                    }
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
                int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
                box.move(dx, dy);
            }
        }
        return false;
    }

    public void checkCompletion() {
        int numberOfHomes = gameObjects.getHomes().size();
        int numberOfHomesWithBox = 0;

        for (Home home : gameObjects.getHomes()) {
            for (Box box : gameObjects.getBoxes()) {
                if (box.getX() == home.getX() && box.getY() == home.getY()) {
                    numberOfHomesWithBox++;
                }
            }
        }

        if (numberOfHomesWithBox == numberOfHomes) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    public void move(Direction direction) {
        if (checkWallCollision(gameObjects.getPlayer(), direction)) {
            return;
        }

        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }

        int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
        int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
        gameObjects.getPlayer().move(dx, dy);

        checkCompletion();
    }


    public Model() {
        try {
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        } catch (URISyntaxException e) {
        }
    }

    public static final int FIELD_CELL_SIZE = 20;

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel += 1;
        restart();
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
