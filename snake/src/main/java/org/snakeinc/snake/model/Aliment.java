package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.text.Position;
import javax.swing.Timer;

import lombok.Getter;
import org.snakeinc.snake.GamePanel;

public abstract class Aliment {

    @Getter
    private Tile position;
    private final Random random;
    Color color;

    public Aliment() {
        random = new Random();
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    private Tile newRandomTile(){
        return new Tile(random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE - 1)),
                        random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1));
    }

    private int getDistance(Tile position, Snake snake){
        return (int) Math.sqrt(Math.pow(position.getX() - snake.getHead().getX(), 2) + Math.pow(position.getY() - snake.getHead().getY(), 2));
    }

    private boolean closeToBorder(Tile position, int distance){
        int x = position.getX();
        int y = position.getY();
        if((x > distance && x < GamePanel.N_TILES_X  - distance) || (y > distance && y < GamePanel.N_TILES_Y - distance)){
            return false;
        }
        else{
            return true;
        }
    }

    public abstract void beEatenBy(AlimentEater alimentEater);

    public void updateLocation(Snake snake, Difficulty difficulty) {
        switch(difficulty.getMode()) {
            case EASY: //aliments appear close to the head of the snake
                position = newRandomTile();
                while (snake.getBody().contains(position) || getDistance(position, snake) > 4 ){ //l'aliment ne doit pas être caché par le serpent ni trop éloigné de sa tête
                    position = newRandomTile();
                }
                break;
            case NORMAL:
                position = newRandomTile();
                while(snake.getBody().contains(position)){ // l'aliment ne doit pas être caché par le serpent
                    position = newRandomTile();
                }
                break;
            case HARD: // aliments proches de la bordure
                position = newRandomTile();
                while(snake.getBody().contains(position) || !closeToBorder(position, 4)){ // l'aliment ne doit pas être caché par le serpent
                    position = newRandomTile();
                }
                break;
        }
        
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        position.drawOval(g);
    }

}
