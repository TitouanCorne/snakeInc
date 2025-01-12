package org.snakeinc.snake.model;
import java.awt.Color;

import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.Snake;

import lombok.Getter;


public class Anaconda extends Snake {
    

    public Anaconda(){
        super();
        setColor(Color.gray);
        setName("anaconda");
    }

    @Override
    public void eat(Apple apple) {
        body.add(apple.getPosition());
        body.add(apple.getPosition());        
    }

    @Override
    public void eat(Broccoli broccoli) {
        body.removeLast();        
    }

    @Override
    public boolean canEatBroccoli(){
        if(this.getBody().size() <= 1){ //si c'est un broccoli et que le serpent est trop petit
            return false;
        }else{
            return true;
        }
    }
}
