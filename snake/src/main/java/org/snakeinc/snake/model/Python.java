package org.snakeinc.snake.model;
import java.awt.Color;

import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.Snake;

import lombok.Getter;


public class Python extends Snake {
    

    public Python(){
        super();
        setColor(Color.green);
        setName("Python");
    }

    // @Override
    // public void eat(Aliment aliment) {
    //     if(aliment.color == Color.red){ //si c'est une pomme
    //         body.add(aliment.getPosition());
    //     }
    //     else{ //c'est un broccoli
    //         body.removeLast();
    //         body.removeLast();
    //     }
    // }

    @Override
    public void eat(Apple apple) {
        body.add(apple.getPosition());
    }

    @Override
    public void eat(Broccoli broccoli) {
        body.removeLast();
        body.removeLast();
    }

    @Override
    public boolean canEatBroccoli(){
        if(this.getBody().size() <= 2){ //si c'est un broccoli et que le serpent est trop petit
            return false;
        }else{
            return true;
        }
    }
}
