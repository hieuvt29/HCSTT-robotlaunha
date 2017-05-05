/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author 8TITTIT8
 */
public class Floor {
    private ArrayList<Entity> Entities = new ArrayList<>();
    
    public Floor() {
        Brick brick;
        for(int i = 0; i < 10; ++i){
            brick = new Brick(20 + 2, i*60 + 20 + 2);
            this.Entities.add(brick);
            brick = new Brick(560 + 2, i*60 + 20 + 2);
            this.Entities.add(brick);
        }
        for(int i = 1; i < 9; ++i) {
            brick = new Brick(i*60 + 20 + 2, 20 + 2);
            this.Entities.add(brick);
            brick = new Brick(i*60 + 20 + 2, 560 + 2);
            this.Entities.add(brick);
        }
    }
    
    public ArrayList<Entity> getEntities() {
        return this.Entities;
    }
    
    public void addEntity (Entity e) {
        this.Entities.add(e);
    }
    public void update(){
        for(Entity e : this.Entities){
            if(e instanceof RunObstacle){
                ((RunObstacle) e).update();
            }
        }
    }
    public void render(Graphics2D g){
        // draw 
        ArrayList<Entity> cloneEntities = new ArrayList<>(this.Entities);
        
        g.drawImage(Assets.background, 20, 20, null);
        for(Entity e : cloneEntities)
            if (e instanceof Brick) e.render(g);
        
        // draw entities
        for(Entity e : cloneEntities)
            if (e instanceof Dirty) e.render(g);
        for(Entity e : cloneEntities)
            if (e instanceof Box) e.render(g);
        for(Entity e : cloneEntities)
            if (e instanceof RunObstacle) e.render(g);
    }
}
