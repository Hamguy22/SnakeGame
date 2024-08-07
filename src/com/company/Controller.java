package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Controller {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int SQUARE_SIZE = 25;
    private static final int WINDOW_WIDTH = WIDTH * SQUARE_SIZE;
    private static final int WINDOW_HEIGHT = HEIGHT * SQUARE_SIZE;
    private static final int DELAY = 300;

    private View view;
    private Graphics graphics;
    private Direction currentDirection = Direction.RIGHT;
    private Location appleLocation;
    private List<Location> snakeLocationList = new ArrayList<>();
    private Direction oldDirection = currentDirection;

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        view.create(WINDOW_WIDTH, WINDOW_HEIGHT);
        snakeLocationList.add(new Location(WIDTH / 2, HEIGHT / 2));
        moveApple();
        run();
    }


    private void render() {
        BufferedImage bufferedImage = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.getGraphics();
        drawSnake();
        drawSquare(appleLocation, Color.RED);
        graphics.drawString(String.valueOf(snakeLocationList.size() - 1), 1, SQUARE_SIZE);
        view.setImage(bufferedImage);
    }

    private void drawSnake() {
        snakeLocationList.forEach(l -> drawSquare(l, Color.GREEN));
    }

    private void drawSquare(Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void run() {
        while (true) {
            render();
            delay();
            Location head = new Location(snakeLocationList.getLast());
            head.move(currentDirection);
            head.normalize(WIDTH, HEIGHT);
            if (snakeLocationList.contains(head)) {
                return;
            }
            oldDirection = currentDirection;
            snakeLocationList.add(head);
            if (head.equals(appleLocation)) {
                moveApple();
                continue;
            }
            snakeLocationList.removeFirst();
        }
    }



    private int random(int max) {
        return (int) (Math.random() * max);
    }

    private void moveApple() {
//        appleLocation = Stream.generate(() -> new Location(random(WIDTH), random(HEIGHT)))
//                .filter(l -> !snakeLocationList.contains(l))
//                .findFirst()
//                .get();
        do {
            appleLocation = new Location(random(WIDTH), random(HEIGHT));
        } while (snakeLocationList.contains(appleLocation));
    }

    public void handleKeyPress(int keyCode) {
        Direction tempDirection = currentDirection;
        switch (keyCode) {
            case KeyEvent.VK_W -> tempDirection = Direction.UP;
            case KeyEvent.VK_A -> tempDirection = Direction.LEFT;
            case KeyEvent.VK_S -> tempDirection = Direction.DOWN;
            case KeyEvent.VK_D -> tempDirection = Direction.RIGHT;
        }
        if (!tempDirection.isOpposite(oldDirection)) {
            currentDirection = tempDirection;
        }
    }

    private void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
