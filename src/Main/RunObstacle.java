/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.MainController;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author 8TITTIT8
 */
public class RunObstacle extends Entity {

    private int velX, velY;
    private Random rand;
    private Floor floor;
    private Robot robot;
    
    public RunObstacle(int x, int y, Game game) {
        super(x, y);
        rand = new Random();
        this.velX = rand.nextInt(3) - 1;
        this.velY = 0;
        
        this.floor = game.getFloor();
        this.robot = game.getRobot();
    }

    public void update() {
        Random rand = new Random();
        boolean collision = false;
        boolean wallCollison = false;
        for (Entity e1 : this.floor.getEntities()) {
            if (!(e1 instanceof RunObstacle) && !(e1 instanceof Dirty)) {
                if (MainController.collision(this.getX() + this.getVelX(), this.getY() + this.getVelY(), e1.getX(), e1.getY())) {
                    if (this.getVelX() == 0) {
                        this.setVelX(rand.nextInt(3) - 1);
                        if (this.getVelX() == 0) {
                            this.setVelY(this.getVelY() * -1);
                        } else {
                            this.setVelY(0);
                        }
                    }

                    if (this.getVelY() == 0) {
                        this.setVelY(rand.nextInt(3) - 1);
                        if (this.getVelY() == 0) {
                            this.setVelX(this.getVelX() * -1);
                        } else {
                            this.setVelX(0);
                        }
                    }
                    System.out.println("11111");
                    System.out.println(this.getX() + " " + this.getY() + " " + this.getVelX() + " " + this.getVelY());
                    System.out.println(e1.getX() + " " + e1.getY());
                    wallCollison = true;
                }
            }
        }
        if (this.robot != null) {
            if (MainController.collision(this.robot.getX() + this.robot.getVelX(), this.robot.getY() + this.robot.getVelY(), this.getX() + this.getVelX(), this.getY() + this.getVelY())) {
                this.updateRunning();
                System.out.println("2222");
                this.robot.collisionWithRunObstacle = true;
            }
        }

        if (!wallCollison) {
            this.updateRunning();
        }
    }

    @Override
    public void updateRotation() {

    }

    @Override
    public void updateRunning() {
//        System.out.println(this.velX + " " + this.velY);
        super.setX(super.getX() + this.velX);
        super.setY(super.getY() + this.velY);
        if (((super.getX() - 22) % 60 == 0) && ((super.getY() - 22) % 60 == 0)) {
            if (super.getX() - 22 == 60) {
                this.velX = rand.nextInt(2);
            } else if (super.getX() - 22 == 480) {
                this.velX = rand.nextInt(2) - 1;
            } else {
                this.velX = rand.nextInt(3) - 1;
            }

            if (super.getY() - 22 == 60) {
                this.velY = rand.nextInt(2);
            } else if (super.getY() - 22 == 480) {
                this.velY = rand.nextInt(2) - 1;
            } else {
                this.velY = rand.nextInt(3) - 1;
            }

            int d = rand.nextInt(2);
            if ((this.velX != 0) && (this.velY != 0)) {
                if (d == 0) {
                    this.velX = 0;
                } else {
                    this.velY = 0;
                }
            }
        }
    }

    @Override
    public void render(Graphics g
    ) {
        g.drawImage(Assets.runObstacle, super.getX(), super.getY(), null);
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

}
