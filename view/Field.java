package com.javarush.task.task34.task3410.view;

import javax.swing.*;
import java.awt.*;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.Box;
import com.javarush.task.task34.task3410.model.Home;
import com.javarush.task.task34.task3410.model.Player;
import com.javarush.task.task34.task3410.model.Wall;

public class Field extends JPanel {
    private View view;

    private EventListener eventListener;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public Field(View view) {
        this.view = view;
    }

    @Override
    public void paint(Graphics g) {
        Box box = new Box(120, 40);
        Player player = new Player(100, 50);
        Home home = new Home(160, 50);
        Wall wall = new Wall(160, 90);
        box.draw(g);
        player.draw(g);
        home.draw(g);
        wall.draw(g);
    }
}
