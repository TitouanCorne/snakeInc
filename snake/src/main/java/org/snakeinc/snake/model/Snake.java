package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import lombok.Data;

@Data
public abstract class Snake implements AlimentEater {
    protected String name;
    protected final ArrayList<Tile> body;
    protected Color color;
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    
        public Snake() {
            body = new ArrayList<>();
            color = new Color(192, 192, 192);
            body.add(new Tile(5, 5)); // La tête du serpent    
        }
    
        public ArrayList<Tile> getBody() {
            return body;
        }
    
        public Tile getHead() {
            return body.getFirst();
        }
    
        public void setColor(Color color){
            this.color = color;
        }

    public abstract boolean canEatBroccoli();

    public void move(Direction direction) {
        Tile newHead = getHead().copy();

        switch (direction) {
            case UP:
                newHead.setY(newHead.getY() - 1);
                break;
            case DOWN :
                newHead.setY(newHead.getY() + 1);
                break;
            case LEFT :
                newHead.setX(newHead.getX() - 1);
                break;
            case RIGHT :
                newHead.setX(newHead.getX() + 1);
                break;
        }

        body.addFirst(newHead);
        body.removeLast(); // Supprime le dernier segment pour simuler le déplacement
    }

    public void draw(Graphics g) {
        for (Tile t : body) {
            g.setColor(this.color);
            t.drawRectangle(g);
        }
    }

    public boolean checkSelfCollision() {
        for (int i = 1; i < body.size(); i++) {
            if (getHead().equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision() {
        return !getHead().isInsideGame();
    }

}
