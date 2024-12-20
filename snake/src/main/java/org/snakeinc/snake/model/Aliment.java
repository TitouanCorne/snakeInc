package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
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

    public abstract void beEatenBy(AlimentEater alimentEater);

    public void updateLocation(Snake snake) {
        position = new Tile(random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE - 1)),
                random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1));
        while(snake.getBody().contains(position)){ // tant que l'aliment est caché derrière le serpent
            position = new Tile(random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE - 1)),
                random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1));
        }
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        position.drawOval(g);
    }

}
