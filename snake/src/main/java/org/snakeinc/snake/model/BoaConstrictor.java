package org.snakeinc.snake.model;
import java.awt.Color;

import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.Snake;

import lombok.Getter;


public class BoaConstrictor extends Snake {
    

    public BoaConstrictor(){
        super();
        setColor(Color.blue);
    }

    
    // @Override
    // public void eat(Aliment aliment) {
    //     if(aliment.color == Color.red){ //si c'est une pomme
    //         body.add(aliment.getPosition());
    //         body.add(aliment.getPosition());
    //         body.add(aliment.getPosition());
    //     }
    //     // on fait rien si c'est un broccoli
    // }

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
}
