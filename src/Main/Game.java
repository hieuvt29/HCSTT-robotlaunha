/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.MainController;
import static controllers.MainController.collision;
import game.display.Display;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 8TITTIT8
 */
public class Game implements Runnable {

    public static ArrayList<Integer> node;
    public static ArrayList<Integer> father;
    public static ArrayList<Integer> nodeX;
    public static ArrayList<Integer> nodeY;

//    public static ArrayList<Integer> action;
    public static ArrayList<Integer> root;
    public static ArrayList<Integer> cellX;
    public static ArrayList<Integer> cellY;
    public static int[][][][][][][][] status = new int[7][4][2][2][2][2][2][2];
    public static ArrayList<int[]> movement = new ArrayList<>();
    public static Floor floor;
    public static Robot robot;
    public static int currentNode;
//    public static int currentAction = -1000000000;
    public static boolean isRunning = false;

    private int width;
    private int height;
    private String title;
    private Canvas canvas;
    private volatile Thread thread;
    private boolean running;
    private Graphics g;
    private BufferStrategy bs;
    private int sparkle;

    public Game(String title) {
        this.width = 600;
        this.height = 600;
        this.title = title;
        this.sparkle = 1;
    }

    public void init() {
        Assets.init();
    }

    public void update() {
        robot.collisionWithRunObstacle = false;

        if (isRunning == true) {
            // xử lí va chạm vật cản di động và vật cản cô định
            //check collision with wall
            //check collision with robot?collision = true: collision = false
            floor.update();
            if (robot.collisionWithRunObstacle) {
                return;
            }
            // update robot
            robot.update();
        }
    }

    public void render() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        //c clear screen
        g.clearRect(0, 0, width, height);

//        // draw 
//        ArrayList<Entity> cloneEntities = new ArrayList<>(this.floor.getEntities());
//        
//        g.drawImage(Assets.background, 20, 20, null);
//        for(Entity e : cloneEntities)
//            if (e instanceof Brick) e.render(g);
        this.floor.render(g2); // refactor code
        //draw spanning tree
        g.setColor(Color.RED);
        g2.setStroke(new BasicStroke(6));
        for (int i = 1; i < Game.node.size(); ++i) {
            //System.out.println("coordinate: " + Game.nodeX.get(i) + " " + Game.nodeY.get(i) + " " + Game.nodeX.get(Game.father.get(i)) + " " + Game.nodeY.get(Game.father.get(i)));
            g2.drawLine(Game.nodeX.get(i), Game.nodeY.get(i), Game.nodeX.get(Game.father.get(i)), Game.nodeY.get(Game.father.get(i)));
        }
        g.setColor(null);

        // draw pass cell
        this.sparkle = (this.sparkle + 1) % 120;
        for (int i = 0; i < Game.cellX.size(); ++i) {
            if (this.sparkle < 40) {
                g2.drawImage(Assets.sparkle, Game.cellX.get(i) + 2, Game.cellY.get(i) + 2, null);
                continue;
            }
            if (this.sparkle < 80) {
                g2.drawImage(Assets.sparkle1, Game.cellX.get(i) + 2, Game.cellY.get(i) + 2, null);
                continue;
            }
            g2.drawImage(Assets.sparkle2, Game.cellX.get(i) + 2, Game.cellY.get(i) + 2, null);
        }

//        // draw entities
//        for(Entity e : cloneEntities)
//            if (e instanceof Dirty) e.render(g);
//        for(Entity e : cloneEntities)
//            if (e instanceof Box) e.render(g);
//        for(Entity e : cloneEntities)
//            if (e instanceof RunObstacle) e.render(g);
        // draw robot
        if (this.robot != null) {
            this.robot.render(g);
        }

        // end
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        int fps = 120;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                delta--;
            }
        }

        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public static Floor getFloor() {
        return floor;
    }

    public static Robot getRobot() {
        return robot;
    }
}
