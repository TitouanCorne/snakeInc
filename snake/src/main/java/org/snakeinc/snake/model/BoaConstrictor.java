package org.snakeinc.snake.model;
import java.awt.Color;

import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.Snake;

import lombok.Getter;


public class BoaConstrictor extends Snake {
    

    public BoaConstrictor(){
        super();
        setColor(Color.blue);
        setName("BoaConstrictor");;
    }


    @Override
    public void eat(Apple apple) {
        body.add(apple.getPosition());
        body.add(apple.getPosition());
        body.add(apple.getPosition());
    }

    @Override
    public void eat(Broccoli broccoli) {
        //Ne fait rien
    }

    @Override
    public boolean canEatBroccoli(){
        return true; //le boa peut tout manger
    }
}
