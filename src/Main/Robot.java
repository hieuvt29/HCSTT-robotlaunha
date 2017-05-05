/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.MainController;
import game.display.Display;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author 8TITTIT8
 */
public class Robot extends Entity {

    private int velX = 1;
    private int velY = 1;
    private int angle = 0;
    private boolean isRunning;
    private boolean isDoing;
    private int isSpinning;

    public static ArrayList<Integer> action;
    public static int currentNode;
    public static int currentAction = -1000000000;
    public static boolean collisionWithRunObstacle = false;
    private Floor floor;

    public Robot(int x, int y, Game game) {
        super(x, y);
        this.floor = game.getFloor();
    }

    @Override
    public void updateRotation() {
        int goalAngle = this.angle;
        ArrayList<Integer> rootClone = new ArrayList<>(Game.root);

        if (rootClone.get(Robot.currentAction) == 0) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 90;
            }
        }
        if (rootClone.get(Robot.currentAction) == 1) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 180;
            }
        }
        if (rootClone.get(Robot.currentAction) == 2) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 270;
            }
        }
        if (rootClone.get(Robot.currentAction) == 3) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 0;
            }
        }
        //System.out.println(this.angle + " " + goalAngle + " " + Robot.currentAction + " " + rootClone.get(Robot.currentAction) + " " + Robot.action.get(Robot.currentAction));
        if (this.angle != goalAngle) {
            if (((360 + this.angle - goalAngle) % 360) > ((360 + goalAngle - this.angle) % 360)) {
                this.angle = (this.angle + 1) % 360;
            } else {
                this.angle = (this.angle - 1 + 360) % 360;
            }
        } else {
            this.isRunning = true;
            this.isDoing = false;
        }

    }

    public void updateSpinning() {
        this.angle = (this.angle + 1) % 360;
        int goalAngle = this.angle;
        ArrayList<Integer> rootClone = new ArrayList<>(Game.root);

        if (rootClone.get(Robot.currentAction) == 0) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 90;
            }
        }
        if (rootClone.get(Robot.currentAction) == 1) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 180;
            }
        }
        if (rootClone.get(Robot.currentAction) == 2) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 0;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 270;
            }
        }
        if (rootClone.get(Robot.currentAction) == 3) {
            if (Robot.action.get(Robot.currentAction) == 0) {
                goalAngle = 90;
            }
            if (Robot.action.get(Robot.currentAction) == 1) {
                goalAngle = 180;
            }
            if (Robot.action.get(Robot.currentAction) == 2) {
                goalAngle = 270;
            }
            if (Robot.action.get(Robot.currentAction) == 3) {
                goalAngle = 0;
            }
        }
        if (goalAngle == this.angle) {
            if (this.isSpinning > 0) {
                this.isSpinning--;
            }
        }
    }

    public void updateRunning() {
        ArrayList<Integer> cellXClone = new ArrayList<>(Game.cellX);
        ArrayList<Integer> cellYClone = new ArrayList<>(Game.cellY);
        boolean ok = true;

        if (this.angle == 0) {
            super.setY(super.getY() + 1);
            if ((super.getY() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 90) {
            super.setX(super.getX() - 1);
            if ((super.getX() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 180) {
            super.setY(super.getY() - 1);
            if ((super.getY() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 270) {
            super.setX(super.getX() + 1);
            if ((super.getX() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }

    }

    public void update() {
        ArrayList<Integer> actionClone = new ArrayList<>(this.action);
        if (this.currentAction != -1000000000) {

            if (this.getIsDoing()) {
                this.updateRotation();
            }
            if (this.getIsRunning()) {
                this.updateRunning();
            }
            if (this.getIsSpinning() != 0) {
                this.updateSpinning();
            }
            if (!this.getIsDoing() && !this.getIsRunning()) {

                for (Entity e : this.floor.getEntities()) {
                    if (e instanceof Water) {
                        if ((e.getX() - 5 == this.getX() - 2) && (e.getY() - 5 == this.getY() - 2)) {
                            this.setIsSpinning(1);
                            this.floor.getEntities().remove(e);
                            break;
                        }
                    }
                    if (e instanceof Dust) {
                        if ((e.getX() - 5 == this.getX() - 2) && (e.getY() - 5 == this.getY() - 2)) {
                            this.setIsSpinning(2);
                            this.floor.getEntities().remove(e);
                            break;
                        }
                    }
                }

                if (this.getIsSpinning() == 0) {
                    if (this.currentAction == actionClone.size() - 1) {
                        MainController.nextStep();
                    } else {
                        this.currentAction++;
                        this.setIsDoing(true);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.YELLOW);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.6));

        //
        AffineTransform backup = g2.getTransform();
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(this.angle), super.getX() + 28, super.getY() + 28);
        g2.transform(trans);
        g2.fillArc(super.getX() + 28 - 80, super.getY() + 28 - 80, 160, 160, 150, 240);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
        g2.drawImage(Assets.robot, super.getX(), super.getY(), null);
        g2.setTransform(backup);

    }

    // A* ALGORITHM: FIND PATH BACK TO CHARGING POSITION
    public LinkedList<Node> openList = new LinkedList<>();
    public LinkedList<Node> closedList = new LinkedList<>();
    public Node[][] nodes = null;
    public boolean foundTarget = false;
    public Node current = null;

    public LinkedList<Node> getAdjacent(Node current) {
        LinkedList<Node> adj = new LinkedList<>();
        int x = current.getX();
        int y = current.getY(); 
        
        return adj;
    }

    public Node lowestFInOpen() {
        Collections.sort(openList, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.Fcost < n2.Fcost) {
                    return -1;
                } else if (n1.Fcost == n2.Fcost) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return openList.get(0);
    }

    public LinkedList<Node> calcPath(Node startNode, Node targetNode) {
        LinkedList<Node> path = new LinkedList<Node>();
        path.add(targetNode);
        Node parent = targetNode.getParent();
 
        while (parent.getX()!= startNode.getX() && parent.getY() != startNode.getY()) {
            path.add(parent);
            parent = parent.getParent();
        }

        JOptionPane.showMessageDialog(null, " Đã tìm thấy đường đi", "Information", JOptionPane.INFORMATION_MESSAGE);
        return path;
    }

    public LinkedList<Node> findPath(Node startNode, Node targetNode) {
        openList = new LinkedList<Node>();
        closedList = new LinkedList<Node>();
        openList.add(startNode); // add starting node to open list

        foundTarget = false;
        Node current;
        while (!foundTarget) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            if ((current.getX() == targetNode.getX()) && (current.getY() == targetNode.getY())) { // found goal
                foundTarget = true;
                return calcPath(startNode, current);
            }

            // for all adjacent nodes:
            LinkedList<Node> adjacentNodes = getAdjacent(current);
            for (int i = 0; i < adjacentNodes.size(); i++) {
                Node currentAdj = adjacentNodes.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setParent(current); // set current node as previous for this node
                    currentAdj.setHcost(targetNode); // set h costs of this node (estimated costs to goal)
                    currentAdj.setGcost(current); // set g costs of this node (costs from start to this node)
                    currentAdj.calcFcost();
                    openList.add(currentAdj); // add node to openList
                } // else node is in openList                 
                else if (currentAdj.getGcost() > currentAdj.getGcost() + 10) {
                    // costs from current node are cheaper than previous costs
                    currentAdj.setParent(current); // set current node as previous for this node
                    currentAdj.setGcost(current); // set g costs of this node (costs from start to this node)
                    currentAdj.calcFcost();
                }
            }

            if (openList.isEmpty()) { // no path exists
                JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi", "Path Finder", JOptionPane.INFORMATION_MESSAGE);
                return new LinkedList<Node>(); // return empty list
            }
        }
        JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi", "Path Finder", JOptionPane.INFORMATION_MESSAGE);
        return null; // unreachable
    }


    public int getVelX() {
        if (this.angle == 90) {
            return -1;
        }
        if (this.angle == 270) {
            return 1;
        }
        return 0;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        if (this.angle == 0) {
            return 1;
        }
        if (this.angle == 180) {
            return -1;
        }
        return 0;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean getIsDoing() {
        return isDoing;
    }

    public void setIsDoing(boolean isDoing) {
        this.isDoing = isDoing;
    }

    public int getIsSpinning() {
        return isSpinning;
    }

    public void setIsSpinning(int isSpinning) {
        this.isSpinning = isSpinning;
    }

}
