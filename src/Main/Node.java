/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author HCB
 */
public class Node {

    private boolean start;
    private boolean target;

    private int x, y;
    private Node parent;
    private boolean onPath;

    public double Hcost, Gcost, Fcost;
    public Node left, right, up, down;
    private Node subleftup, subrightup, subleftdown, subrightdown;
    public ArrayList<Node> child = new ArrayList<Node>();
    
    public Node(int x, int y) {
        start = false;
        target = false;
        this.x = x;
        this.y = y;

    }


    public void setStart(boolean start) {
        this.start = start;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isTarget() {
        return target;
    }


    //  Tinh chi phi  cho node hien tai
    public void setHcost(Node target) {
        Hcost = (Math.pow(x - target.getX(), 2) + Math.pow(y - target.getY(), 2)) * 10;
    }

    public void setGcost(Node currentNode) {
        this.Gcost = currentNode.Gcost + 10;
    }
    public void calcFcost(){
        this.Fcost = this.Hcost + this.Gcost;
    }
    
    public Node traceBack() {
        onPath = true;
        return parent;
    }
    
    // sub node
    public void setDirections(Node l, Node r, Node u, Node d) {
        left = l;
        right = r;
        up = u;
        down = d;
    }

    public void setSub(Node l, Node r, Node u, Node d) {
        subleftup = l;
        subrightup = r;
        subleftdown = u;
        subrightdown = d;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getFcost() {
        return Fcost;
    }

    public int getPos(Node node) {
        int id = 0;
        if (node == getUp()) {
            id = 1;
        } else if (node == getRight()) {
            id = 2;
        } else if (node == getDown()) {
            id = 3;
        } else if (node == getLeft()) {
            id = 4;
        }
        return id;
    }
    public double getHcost() {
        return Hcost;
    }

    public double getGcost() {
        return Gcost;
    }
    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getUp() {
        return up;
    }

    public Node getDown() {
        return down;
    }

    public Node getSubL_U() {
        return subleftup;
    }

    public Node getSubR_U() {
        return subrightup;
    }

    public Node getSubL_D() {
        return subleftdown;
    }

    public Node getSubR_D() {
        return subrightdown;
    }
}
